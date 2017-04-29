package pitayaa.nail.msg.core.employee.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pitayaa.nail.domain.employee.Employee;

public interface EmployeeService {

	List<Employee> findAllEmployee(String salonId);

	Optional<Employee> findOne(UUID id);

	Employee save(Employee employeeBody) throws Exception;
	
	Optional <Employee> findEmployee(String salonId,String pin);

	Employee update(Employee employeeSaved, Employee employeeUpdated) throws Exception;
}
