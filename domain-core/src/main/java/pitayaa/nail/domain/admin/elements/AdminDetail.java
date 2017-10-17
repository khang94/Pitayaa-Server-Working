package pitayaa.nail.domain.admin.elements;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class AdminDetail {
	private String firstName;
	private String lastName;
	private String businessName;
}
