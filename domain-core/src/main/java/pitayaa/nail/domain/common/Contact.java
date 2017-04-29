package pitayaa.nail.domain.common;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Contact {

	private String homePhone;
	private String mobilePhone;
	private String email;
	private String fax;

}
