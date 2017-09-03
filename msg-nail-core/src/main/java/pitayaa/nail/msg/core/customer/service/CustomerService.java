package pitayaa.nail.msg.core.customer.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.hibernate.transaction.QueryCriteria;

public interface CustomerService {

	List<Customer> findAllCustomer(String salonId);

	Customer save(Customer customer) throws Exception;

	Optional<Customer> findOne(UUID id);

	void delete(Customer customer);

	List<?> findAllByQuery(QueryCriteria query) throws ClassNotFoundException;

	List<Customer> findAllCustomer(String salonId, String type);

	Optional<Customer> login(String email, String password, String salonId);

	Optional<Customer> findByQrcode(String qrcode, String salonId);

	Customer signIn(Customer customerBody) throws Exception ;

	Customer update(Customer customerSaved, Customer customerUpdated) throws Exception;

	Customer updatePassword(Customer customerSaved, Customer customerUpdated, String oldPass) throws Exception;

	List<Customer> findCustomerByCondition(String salonId, Date from , Date to) throws Exception;

	List<Customer> findCustomerByCondition(String salonId, String customerType, Date from , Date to)
			throws Exception;
}
