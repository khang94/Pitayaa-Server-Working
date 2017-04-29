package pitayaa.nail.domain.employee.elements;

import java.util.Date;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class EmployeeDetail {

	private String firstName;
	private String lastName;
	private String birthDay;
	private Date lastCheckin;
	private String employeeType; //
	private int status;
	private String position;
	private String employeeLevel; // Depend for owner Salon design
}
