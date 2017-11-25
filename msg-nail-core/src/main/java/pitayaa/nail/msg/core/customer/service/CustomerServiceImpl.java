package pitayaa.nail.msg.core.customer.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.hibernate.transaction.QueryCriteria;
import pitayaa.nail.domain.membership.MembershipManagement;
import pitayaa.nail.msg.business.customer.CustomerServiceBusiness;
import pitayaa.nail.msg.business.util.security.EncryptionUtils;
import pitayaa.nail.msg.core.common.CoreConstant;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.customer.repository.CustomerRepository;
import pitayaa.nail.msg.core.hibernate.CriteriaRepository;
import pitayaa.nail.msg.core.hibernate.SearchCriteria;
import pitayaa.nail.msg.core.membership.service.MembershipService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	CriteriaRepository criteriaRepo;

	@Autowired
	CustomerViewService viewService;

	@Autowired
	CoreHelper coreHelper;

	@Autowired
	CustomerServiceBusiness customerServiceBusiness;
	
	@Autowired
	MembershipService membershipService;

	@Override
	public List<Customer> findAllCustomer(String salonId) {
		return customerRepo.findAllCustomer(salonId);
	}

	@Override
	public List<Customer> findAllCustomer(String salonId, String type) {
		List<Customer> customers = new ArrayList<Customer>();
		if (!type.equalsIgnoreCase("")) {
			customers = customerRepo.findAllCustomer(salonId, type);
		} else {
			customers = customerRepo.findAllCustomer(salonId);
		}
		return customers;
	}

	@Override
	public Customer save(Customer customerBody) throws Exception {

		byte[] binaryImg = null;
		boolean isUploadImage = false;

		// Get stream image
		if (customerBody.getView().getImgData() != null && customerBody.getView().getImgData().length > 0) {
			binaryImg = customerBody.getView().getImgData();
			isUploadImage = true;
		}
		
		MembershipManagement membership = membershipService.createMembershipForNewCustomer(customerBody);
		customerBody.getCustomerMembership().setMembershipId(membership.getUuid().toString());
		
		// Hide image
		customerBody.getView().setImgData(null);
		
		// Save to database
		customerBody = customerRepo.save(customerBody);


		if (isUploadImage && customerBody.getSalonId() != null) {
			viewService.buildViewByDate(customerBody, binaryImg);
		}

		return customerBody;
	}

	@Override
	public List<Customer> findCustomerByCondition(String salonId, Date from, Date to) throws Exception {
		return customerRepo.findAllCustomer(salonId, from, to);
	}

	@Override
	public List<Customer> findCustomerByCondition(String salonId, String customerType, Date from, Date to)
			throws Exception {
		return customerRepo.findAllCustomer(salonId, customerType, from, to);
	}

	@Override
	public Customer updatePassword(Customer customerSaved, Customer customerUpdated, String oldPass) throws Exception {

		byte[] binaryImg = null;
		boolean isUpdatedImage = false;

		// Update New Image
		if (customerUpdated.getView().getImgData() != null && customerUpdated.getView().getImgData().length > 0) {
			isUpdatedImage = true;
			binaryImg = customerUpdated.getView().getImgData();
		}

		// Hide image
		customerUpdated.getView().setImgData(null);

		if (isUpdatedImage && customerSaved.getView().getImgData() != null) {
			// Delete image from static path in local server
			coreHelper.deleteFile(customerSaved.getView().getPathImage());
		}

		// update password
		if (customerUpdated.getPassword() != null && customerUpdated.getContact().getEmail().length() > 0) {
			String oldPassEncrypt = EncryptionUtils.encodeMD5(oldPass, customerUpdated.getContact().getEmail());
			if (!oldPassEncrypt.equals(customerSaved.getPassword())) {
				throw new Exception("Wrong Old password");
			}

			String encryptionPassword = EncryptionUtils.encodeMD5(customerUpdated.getPassword(),
					customerUpdated.getContact().getEmail());

			customerUpdated.setPassword(encryptionPassword);
		} else {
			customerUpdated.setPassword(customerSaved.getPassword());
		}

		// Update hibernate
		customerUpdated.setUuid(customerSaved.getUuid());
		customerUpdated = customerRepo.save(customerUpdated);

		if (isUpdatedImage && customerUpdated.getSalonId() != null) {
			viewService.buildViewByDate(customerUpdated, binaryImg);
		}

		return customerUpdated;
	}

	@Override
	public Customer update(Customer customerSaved, Customer customerUpdated) throws Exception {

		// Update security information
		customerUpdated.setQrCode(customerSaved.getQrCode());
		customerUpdated.setPassword(customerSaved.getPassword());

		byte[] binaryImg = null;
		boolean isUpdatedImage = false;

		// Update New Image
		if (customerUpdated.getView().getImgData() != null && customerUpdated.getView().getImgData().length > 0) {
			isUpdatedImage = true;
			binaryImg = customerUpdated.getView().getImgData();
		}

		// Hide image
		customerUpdated.getView().setImgData(null);

		if (isUpdatedImage && customerSaved.getView().getImgData() != null) {
			// Delete image from static path in local server
			coreHelper.deleteFile(customerSaved.getView().getPathImage());
		}

		// Update hibernate
		customerUpdated.setUuid(customerSaved.getUuid());
		customerUpdated = customerRepo.save(customerUpdated);

		if (isUpdatedImage && customerUpdated.getSalonId() != null) {
			viewService.buildViewByDate(customerUpdated, binaryImg);
		}

		return customerUpdated;
	}

	@Override
	public Optional<Customer> findOne(UUID id) {
		return Optional.ofNullable(customerRepo.findOne(id));
	}

	@Override
	public void delete(Customer customer) {
		customerRepo.delete(customer);
	}

	@Override
	public List<?> findAllByQuery(QueryCriteria query) throws Exception {
		SearchCriteria sc = criteriaRepo.extractQuery(query.getQuery());
		sc.setEntity(query.getObject());
		return criteriaRepo.searchCriteria(sc);
	}

	@Override
	public Optional<Customer> login(String email, String password, String salonId) {
		return Optional.ofNullable(customerRepo.findByEmailAndPassword(email, password, salonId));
	}

	@Override
	public Optional<Customer> findByQrcode(String qrcode, String salonId) {
		return Optional.ofNullable(customerRepo.findByQrcode(qrcode, salonId));
	}
	
	@Override
	public Customer generateQrCode(Customer customerBody) throws Exception {
		String qrCode = UUID.randomUUID().toString();
		customerBody.setQrCode(qrCode);
		
		return customerBody;
	}
	
	@Override 
	public Customer generatePassword (Customer customerBody) throws Exception {
		
		// Encrypt password
		if (customerBody.getContact().getEmail() != null && customerBody.getContact().getEmail().length() > 0) {
			String encryptionPassword = EncryptionUtils.encodeMD5("123456", customerBody.getContact().getEmail());
			customerBody.setPassword(encryptionPassword);
		}
		
		return customerBody;
	}
	
	@Override
	public Customer findCustomerByIdOrPhoneNumber(Customer customerBody) throws Exception{
		
		Customer customer = null;
		
		if(customerBody.getUuid() == null){
			String phoneNumber = customerBody.getContact().getMobilePhone().trim();
			customer = customerRepo.findByPhoneNumber(phoneNumber,customerBody.getSalonId());
		} else {
			customer = customerRepo.findOne(customerBody.getUuid());
		}
		
		return customer;
	}

	@Override
	public Customer signInNew(Customer customerBody) throws Exception {

		// Generate qr code for customer
		this.generateQrCode(customerBody);
		
		// Generate password
		this.generatePassword(customerBody);

		// Save
		customerBody = customerRepo.save(customerBody);

		return customerBody;
	}
	
	@Override
	public Customer signIn(Customer customerBody) throws Exception {

		// Save
		customerBody = customerRepo.save(customerBody);

		return customerBody;
	}
	
	@Override
	public Customer processResponseForCustomer(Customer customerBody , String response) throws Exception {
		
		// Update response
		customerBody.getCustomerDetail().setRespond(response);
		
		// Save
		return customerRepo.save(customerBody);

	}
	
	@Override
	public List<Customer> getCustomersByListId(List<String> listId) throws Exception {
		List<Customer> customers = new ArrayList<>();
		for (String id : listId){
			customers.add(customerRepo.findOne(UUID.fromString(id)));
		}
		return customers;
	}

	
	

}
