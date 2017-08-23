package pitayaa.nail.msg.core.customer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.hibernate.transaction.QueryCriteria;
import pitayaa.nail.msg.business.customer.CustomerServiceBusiness;
import pitayaa.nail.msg.business.util.security.EncryptionUtils;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.customer.repository.CustomerRepository;
import pitayaa.nail.msg.core.hibernate.CriteriaRepository;
import pitayaa.nail.msg.core.hibernate.SearchCriteria;

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
		if (customerBody.getView().getImgData().length > 0) {
			binaryImg = customerBody.getView().getImgData();
			isUploadImage = true;
		}

		try {
			String email = customerBody.getContact().getEmail().toLowerCase();
			customerBody.getContact().setEmail(email);
		}catch (Exception ex){
			ex.getMessage();
		}


		// Generate qr code for customer
		String qrCode = UUID.randomUUID().toString();
		customerBody.setQrCode(qrCode);

		// Encrypt password
		if (customerBody.getContact().getEmail() != null && customerBody.getContact().getEmail().length() > 0) {
			String encryptionPassword = EncryptionUtils.encodeMD5("123456", customerBody.getContact().getEmail());
			customerBody.setPassword(encryptionPassword);
		}

		// Hide image
		customerBody.getView().setImgData(null);

		customerBody = customerRepo.save(customerBody);

		if (isUploadImage && customerBody.getSalonId() != null) {
			viewService.buildViewByDate(customerBody, binaryImg);
		}

		return customerBody;
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
	public List<?> findAllByQuery(QueryCriteria query) throws ClassNotFoundException {
		SearchCriteria sc = criteriaRepo.extractQuery(query.getQuery());
		sc.setEntity(query.getObject());
		return criteriaRepo.searchCriteria(sc);
	}

	@Override
	public Optional<Customer> login(String email, String password, String salonId) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(customerRepo.findByEmailAndPassword(email, password, salonId));
	}

	@Override
	public Optional<Customer> findByQrcode(String qrcode, String salonId) {
		return Optional.ofNullable(customerRepo.findByQrcode(qrcode, salonId));
	}

	@Override
	public Customer signIn(Customer customerBody) throws Exception {

		// Get by phone
		Customer customer = customerRepo.findByPhoneNumber(customerBody.getContact().getMobilePhone());
		if (customer == null) {
			
			// Init default info
			customer = customerServiceBusiness.initDefaultCustomer(customerBody);
			
			// Generate qr code for customer
			String qrCode = UUID.randomUUID().toString();
			customerBody.setQrCode(qrCode);
			LOGGER.info("Qr Code 111[" + qrCode + "]");

			// Encrypt password
			if (customerBody.getContact().getEmail() != null && customerBody.getContact().getEmail().length() > 0) {
				String encryptionPassword = EncryptionUtils.encodeMD5("123456", customerBody.getContact().getEmail());
				customerBody.setPassword(encryptionPassword);
			}
			
			// Update address
			if(customer.getAddress() == null ||customer.getAddress().getAddress() == null) {
				customer.getAddress().setAddress("NONE");
			}

			// Save
			customer = customerRepo.save(customer);
		} else {

			// Update customer
			customerBody.setUuid(customer.getUuid());
			customer = customerRepo.save(customerBody);
		}

		return customer;
	}
}
