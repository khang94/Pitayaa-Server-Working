package pitayaa.nail.msg.business.employee;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import pitayaa.nail.domain.common.Address;
import pitayaa.nail.domain.common.Contact;
import pitayaa.nail.domain.employee.Employee;
import pitayaa.nail.domain.employee.elements.EmployeeDetail;

@Service
public class EmployeeBusImpl implements EmployeeBus {

	@Override
	public List<Employee> getListEmployeeDefault(String salonId) {
		// TODO Auto-generated method stub
		List<Employee> lst = new ArrayList<>();

		// first employee
		Employee model = new Employee();

		Address address = new Address();
		address.setAddress("Chu van an");
		address.setCity("Ho chi minh");
		model.setAddress(address);

		EmployeeDetail employeeDetail = new EmployeeDetail();
		employeeDetail.setBirthDay("26-11");
		employeeDetail.setFirstName("Kelly");
		employeeDetail.setLastName("Nguyen");
		employeeDetail.setStatus(1);
		model.setEmployeeDetail(employeeDetail);

		Contact contact = new Contact();
		contact.setEmail("kellynguyen@gmail.com");
		contact.setHomePhone("123456789");
		model.setContact(contact);
		lst.add(model);

		// second employee

		model = new Employee();

		address = new Address();
		address.setAddress("Chu van an");
		address.setCity("Ho chi minh");
		model.setAddress(address);

		employeeDetail = new EmployeeDetail();
		employeeDetail.setBirthDay("26-11");
		employeeDetail.setFirstName("Andy");
		employeeDetail.setLastName("Tran");
		employeeDetail.setStatus(1);
		model.setEmployeeDetail(employeeDetail);

		contact = new Contact();
		contact.setEmail("andytran@gmail.com");
		contact.setHomePhone("123456789");
		model.setContact(contact);
		lst.add(model);

		return null;
	}

}
