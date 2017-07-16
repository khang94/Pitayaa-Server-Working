package pitayaa.nail.domain.customer.elements;

import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class CustomerDetail {
	
	private String lastUsedServiceId;	
	private String lastPromotionCode;
	
	private String firstName;
	private String lastName;
	private Date birthDay;	
	private Date lastCheckin;
	private String respond;
	private String customerType; // New , Return or Appointment
	private String status;
	private String customerLevel; // VIP or Normal
	
}
