package pitayaa.nail.msg.core.customer.service;

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

	Customer update(Customer customerSaved, Customer customerUpdated) throws Exception;
}
