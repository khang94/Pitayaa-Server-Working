package pitayaa.nail.domain.account.elements;

import java.util.Date;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class AccountDetail {
	
	private String businessName;
	private String ownerName;
	private String firstName;
	private String lastName;
	private Date lastLogin;

}
