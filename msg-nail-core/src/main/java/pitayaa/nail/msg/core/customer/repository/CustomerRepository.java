package pitayaa.nail.msg.core.customer.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.customer.Customer;


@Service
@RepositoryRestResource(collectionResourceRel = "customers", path = "customers")
public interface CustomerRepository extends
		PagingAndSortingRepository<Customer, UUID> { 
	
	@Query("select c from Customer c where c.salonId = :salonId")
	List<Customer> findAllCustomer (@Param("salonId") String salonId);
}
