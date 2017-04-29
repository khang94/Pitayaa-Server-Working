package pitayaa.nail.msg.business.employee;

import java.util.List;

import pitayaa.nail.domain.employee.Employee;

public interface EmployeeBus {
	 List<Employee>getListEmployeeDefault(String salonId);

}
