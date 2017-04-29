package pitayaa.nail.json.customer.elements;

import java.util.Date;

import lombok.Data;

@Data
public class JsonCustomerDetail {

	private String lastUsedServiceId;

	private String firstName;
	private String lastName;
	private Date birthDay;
	private Date lastCheckin;
	private String respond;
	private String customerType; // New , Return or Appointment
	private String status;
	private String customerLevel; // VIP or Normal

}
