package pitayaa.nail.json.account.elements;

import java.util.Date;

import lombok.Data;

@Data
public class JsonAccountDetail {

	private String businessName;
	private String ownerName;
	private String firstName;
	private String lastName;
	private Date lastLogin;

}
