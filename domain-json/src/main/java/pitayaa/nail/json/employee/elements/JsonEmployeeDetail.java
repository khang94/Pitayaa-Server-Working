package pitayaa.nail.json.employee.elements;

import java.util.Date;

import lombok.Data;

@Data
public class JsonEmployeeDetail {

	private String firstName;
	private String lastName;
	private Date birthDay;
	private Date lastCheckin;
	private String employeeType; //
	private String status;
	private String position;
	private String employeeLevel; // Depend for owner Salon design
}
