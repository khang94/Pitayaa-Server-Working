package pitayaa.nail.msg.core.employee.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.employee.Employee;


@Service
@RepositoryRestResource(collectionResourceRel = "employees", path = "employees")
public interface EmployeeRepository extends
		PagingAndSortingRepository<Employee, UUID> { 
	
	@Query("Select emp from Employee emp where emp.salonId = :salonId")
	List<Employee> findAllEmployee(@Param("salonId") String salonId);
	
	@Query("Select emp from Employee emp where emp.salonId = :salonId and emp.password=:password")
	Optional<Employee> findAllEmployee(@Param("salonId") String salonId,@Param("password") String password);
}