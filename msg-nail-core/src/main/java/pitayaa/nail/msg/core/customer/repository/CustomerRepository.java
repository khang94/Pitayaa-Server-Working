package pitayaa.nail.msg.core.customer.repository;

import java.util.Date;
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
	
	@Query("select c from Customer c where c.salonId = :salonId and c.qrCode=:qrCode ")
	Customer findByQrcode (@Param("qrCode") String qrcode,@Param("salonId") String salonId);
	
	@Query("select c from Customer c where c.salonId = :salonId and c.contact.email=:email and c.password=:password ")
	Customer findByEmailAndPassword (@Param("email") String email,@Param("password") String password,@Param("salonId") String salonId);
	
	@Query("select c from Customer c where c.salonId = :salonId and c.customerDetail.customerType = :customerType")
	List<Customer> findAllCustomer(@Param("salonId") String salonId , @Param("customerType") String customerType);
	
	@Query("select c from Customer c where c.salonId = :salonId and c.createdDate between :from and :to")
	List<Customer> findAllCustomer(@Param("salonId") String salonId , @Param("from") Date from , @Param("to") Date to);
	
	@Query("select c from Customer c where c.salonId = :salonId and c.customerDetail.customerType = :customerType and c.createdDate between :from and :to")
	List<Customer> findAllCustomer(@Param("salonId") String salonId,@Param("customerType") String customerType , @Param("from") Date from , @Param("to") Date to);
	
	@Query("select c from Customer c where c.contact.mobilePhone = :phoneNumber and c.salonId = :salonId")
	Customer findByPhoneNumber(@Param("phoneNumber") String phoneNumber,@Param("salonId") String salonId);
	
}
