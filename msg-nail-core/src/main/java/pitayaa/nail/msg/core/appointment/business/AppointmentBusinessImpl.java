package pitayaa.nail.msg.core.appointment.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.appointment.Appointment;
import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.employee.Employee;
import pitayaa.nail.domain.membership.MembershipManagement;
import pitayaa.nail.domain.packages.PackageModel;
import pitayaa.nail.domain.promotion.Promotion;
import pitayaa.nail.domain.service.ServiceModel;
import pitayaa.nail.domain.setting.SettingPromotion;
import pitayaa.nail.domain.setting.promotion.CustomerTurn;
import pitayaa.nail.msg.core.appointment.repository.AppointmentRepository;
import pitayaa.nail.msg.core.common.CoreConstant;
import pitayaa.nail.msg.core.customer.service.CustomerService;
import pitayaa.nail.msg.core.employee.service.EmployeeService;
import pitayaa.nail.msg.core.membership.service.MembershipService;
import pitayaa.nail.msg.core.packageEntity.service.PackageService;
import pitayaa.nail.msg.core.promotion.service.PromotionService;
import pitayaa.nail.msg.core.serviceEntity.service.ServiceEntityInterface;
import pitayaa.nail.msg.core.setting.promotion.service.SettingPromotionService;

@Service
public class AppointmentBusinessImpl implements AppointmentBusiness {

	@Autowired
	CustomerService customerService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	ServiceEntityInterface serviceNail;

	@Autowired
	PackageService packageNail;
	
	@Autowired
	SettingPromotionService settingPromotionService;
	
	@Autowired
	AppointmentRepository appointmentRepo;
	
	@Autowired
	PromotionService promotionService;
	
	@Autowired
	MembershipService membershipService;
	
	/**
	 * Update point reward for customer
	 * @param customer
	 * @return
	 */
	private Customer updateCustomerPoint(Customer customer , String salonId){
		
		// Get promotion setting
		SettingPromotion settingPromotion = settingPromotionService.getSettingPromoteBySalonId(customer.getSalonId());

		// Get point setting
		Integer loyalPoint = settingPromotion.getPointRegularTurn();
		
		// Get turn for target of customer
		CustomerTurn customerTurnRegular = null;
		CustomerTurn customerTurnVIP = null;
		for (CustomerTurn turn : settingPromotion.getCustomerTurn()){
			if(CoreConstant.CUSTOMER_TYPE_LOYAL.equalsIgnoreCase(turn.getTargetType())){
				customerTurnRegular = turn;
			}
		}
		
		// Get current status & point of customer
		Integer currentPoint = customer.getCustomerMembership().getPoint();
		if(currentPoint == null){
			currentPoint = 0;
		}
		
		// Get history of customer
		List<Appointment> appointments = appointmentRepo.findAllTurnCustomer(customer.getContact().getMobilePhone(), salonId);
		
		// Increase point for customers
		Integer pointAdd = currentPoint + loyalPoint;
		
		// Update Status 
		if(appointments.size() >= customerTurnRegular.getTimeTurns()){
			customer.getCustomerDetail().setCustomerType(CoreConstant.CUSTOMER_TYPE_RETURN);
		}
		
		// Add extra point
		customer.getCustomerMembership().setPoint(pointAdd);
		
		return customer;
		
	}

	@Override
	public Appointment executeCreateAppm(Appointment appmBody) throws Exception {

		if (appmBody.getCustomer() != null) {
			appmBody = this.executeCustomer(appmBody.getCustomer().getUuid(),
					appmBody);
		}
		if (appmBody.getEmployee() != null) {
			appmBody = this.executeEmployee(appmBody.getEmployee().getUuid(),
					appmBody);
		}
		if (appmBody.getServicesGroup() != null) {
			appmBody = this.executeServices(appmBody.getServicesGroup(),
					appmBody);
		}
		if (appmBody.getPackagesGroup() != null) {
			appmBody = this.executePackages(appmBody.getPackagesGroup(),
					appmBody);
		}
		if(appmBody.getPromotion() != null && appmBody.getPromotion().getCodeValue() != null && 
				!appmBody.getPromotion().getCodeValue().equalsIgnoreCase("")) {
			appmBody = this.executePromotions(appmBody.getPromotion(), appmBody);
		} else {
			appmBody.setPromotion(null);
		}
		
		// UPDATE STATUS APPOINTMENT
		appmBody.setStatus(AppointmentConstant.BUSINESS_STATUS_READY);
		
		
		return appmBody;
	}

	// Get employee for appointment
	@Override
	public Appointment executeEmployee(UUID employeeId, Appointment appmBody)
			throws Exception {

		Optional<Employee> employee = null;
		
		
		if (employeeId != null) {
			employee = employeeService.findOne(employeeId);

			if (employee.isPresent()) {
				appmBody.setEmployee(employee.get());
			} else {
				throw new Exception(
						"This Employee ID does not exist ! Please check again or create new one .");
			}
		}
		return appmBody;
	}

	// Get customer for appointment
	@Override
	public Appointment executeCustomer(UUID customerId, Appointment appmBody)
			throws Exception {

		Customer customerSaved = null;
		Customer customerInfo = appmBody.getCustomer();
		
		// Get total spending of this visit time
		Double totalSpending = this.getTotalSpendingOfAppointment(appmBody);
		customerInfo.getCustomerMembership().setSpending(totalSpending);
		
		// Update total times visit
		Integer totalTimesVisit = (customerInfo.getCustomerMembership().getTotalTimeVisit() == null) 
								? 1 : customerInfo.getCustomerMembership().getTotalTimeVisit() + 1;
		customerInfo.getCustomerMembership().setTotalTimeVisit(totalTimesVisit);
		
		// Find customer to check whether they have visit first time
		customerSaved = customerService.findCustomerByIdOrPhoneNumber(customerInfo);
		
		// Update point
		customerInfo = this.updateCustomerPoint(customerInfo , appmBody.getSalonId());
		
		if (customerSaved != null){
			membershipService.updateMembershipForReturnCustomer(customerInfo);
			customerInfo = customerService.signIn(customerInfo);
		} else {
			MembershipManagement membership = membershipService.createMembershipForNewCustomer(customerInfo);
			customerInfo.getCustomerMembership().setMembershipId(membership.getUuid().toString());
			customerInfo = customerService.signInNew(customerInfo);
			
			// Update 
			membership.setCustomerId(customerInfo.getUuid().toString());
			membershipService.save(membership);
		}
		
		// Update last check in
		customerInfo.getCustomerDetail().setLastCheckin(new Date());
				
		// Update last service
		ServiceModel serviceUsed = appmBody.getServicesGroup().get(0);
		customerInfo.getCustomerDetail().setLastUsedServiceName(serviceUsed.getServiceName());
		customerInfo.getCustomerDetail().setLastUsedServiceId(serviceUsed.getUuid().toString());
				
		appmBody.setCustomer(customerInfo);
		appmBody.getCustomer().getView().setImgData(null);
		return appmBody;
	}

	
	@Override
	public Double getTotalSpendingOfAppointment(Appointment appmBody) throws Exception{
		
		Double total = 0.0;
		
		// Get summary spending of service
		for(ServiceModel service : appmBody.getServicesGroup()){
			total += service.getPrice1().getPrice();
		}
		
		// Get summary spending of packages
		/*for(PackageModel packageModel : appmBody.getPackagesGroup()){
			total += packageModel.getPrice().getPrice();
		}*/
		
		return total;	
	}

	// Get List services for appointment
	@Override
	public Appointment executeServices(List<ServiceModel> lstService,
			Appointment appmBody) {

		List<ServiceModel> servicesGroup = new ArrayList<ServiceModel>();

		// Get Find services for appointment
		for (ServiceModel service : lstService) {
			if (service.getUuid() != null) {
				boolean isExist = serviceNail.findOne(service.getUuid()).isPresent();
				service = (isExist) ? serviceNail.findOne(service.getUuid()).get() : null;
				if (service != null) {
					servicesGroup.add(service);
				}
			}
		}
		appmBody.setServicesGroup(servicesGroup);
		return appmBody;
	}

	// Get List Packages for appointment
	@Override
	public Appointment executePackages(List<PackageModel> lstPackage,
			Appointment appmBody) {

		List<PackageModel> packagesGroup = new ArrayList<PackageModel>();

		// Get Find packges for appointment
		for (PackageModel p : lstPackage) {
			if (p.getUuid() != null) {
				boolean isExist = packageNail.findOne(p.getUuid()).isPresent();
				p = (isExist) ? packageNail.findOne(p.getUuid()).get() : null;
				if (p != null) {
					packagesGroup.add(p);
				}
			}
		}
		appmBody.setPackagesGroup(packagesGroup);
		return appmBody;
	}
	
	@Override
	public Appointment executePromotions (Promotion promotion , Appointment appmBody) throws Exception{
		
		// Get by code value
		promotion = promotionService.findPromotion(promotion.getSalonId(), promotion.getCodeValue());
		
		// Update promotion
		promotion = promotionService.signInPromotion(promotion.getSalonId(), promotion.getCodeValue());
		
		appmBody.setPromotion(promotion);
		
		// Update customer infor when using promotion code
		this.updateCustomerInfor(promotion.getCustomerId(), appmBody);
		
		return appmBody;
	}
	
	private Appointment updateCustomerInfor(String customerId , Appointment appmBody) throws Exception{
		
		Optional<Customer> customerGetPromotion = customerService.findOne(UUID.fromString(customerId));
		
		if(customerGetPromotion.isPresent()){
			// Update infor for customer sign in
			Customer customerSignIn = appmBody.getCustomer();
			if(!customerId.equalsIgnoreCase(customerSignIn.getUuid().toString())){
				

				customerSignIn.setReferralBy(customerGetPromotion.get().getCustomerDetail().getFirstName() + 
						" " + customerGetPromotion.get().getCustomerDetail().getLastName());
				customerSignIn.setReferralById(customerGetPromotion.get().getUuid().toString());
				customerSignIn.getCustomerDetail().setCustomerType(CoreConstant.CUSTOMER_TYPE_REFERRAL);
				customerService.update(customerSignIn, customerSignIn);	
				appmBody.setCustomer(customerSignIn);
			}
		}
		
		return appmBody;
	}

}
