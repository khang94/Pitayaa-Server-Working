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
import pitayaa.nail.domain.packages.PackageModel;
import pitayaa.nail.domain.service.ServiceModel;
import pitayaa.nail.domain.setting.SettingPromotion;
import pitayaa.nail.domain.setting.promotion.CustomerTurn;
import pitayaa.nail.msg.core.appointment.repository.AppointmentRepository;
import pitayaa.nail.msg.core.common.CoreConstant;
import pitayaa.nail.msg.core.customer.service.CustomerService;
import pitayaa.nail.msg.core.employee.service.EmployeeService;
import pitayaa.nail.msg.core.packageEntity.service.PackageService;
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
	
	
	private Customer updateCustomerPoint(Customer customer){
		
		// Get promotion setting
		SettingPromotion settingPromotion = settingPromotionService.getSettingPromoteBySalonId(customer.getSalonId());

		// Get point setting
		Integer loyalPoint = settingPromotion.getPointRegularTurn();
		Integer promotionPoint = settingPromotion.getPointReferralCode();
		Integer referralPoint = settingPromotion.getPointReferralCode();
		
		// Get turn for target of customer
		CustomerTurn customerTurnRegular = null;
		CustomerTurn customerTurnVIP = null;
		for (CustomerTurn turn : settingPromotion.getCustomerTurn()){
			if(CoreConstant.CUSTOMER_TYPE_LOYAL.equalsIgnoreCase(turn.getTargetType())){
				customerTurnRegular = turn;
			}
		}
		
		// Get current status & point of customer
		String currentStatus = customer.getCustomerDetail().getCustomerType();
		Integer currentPoint = customer.getCustomerMembership().getPoint();
		
		// Get history of customer
		List<Appointment> appointments = appointmentRepo.findAllTurnCustomer(customer.getContact().getEmail());
		
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

		Optional<Customer> customer = null;

		// Get customer for appointment
		if (customerId != null) {
			
			customer = customerService.findOne(customerId);
			customer.get().getCustomerDetail().setLastCheckin(new Date());
			if (customer.isPresent()) {
				Customer customerInfo = this.updateCustomerPoint(customer.get());
				appmBody.setCustomer(customerInfo);
			} else {
				throw new Exception(
						"This Customer ID does not exist ! Please check again or create new one .");
			}
		}
		appmBody.getCustomer().getCustomerDetail().setLastCheckin(new Date());
		appmBody.getCustomer().getView().setImgData(null);
		return appmBody;
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

}
