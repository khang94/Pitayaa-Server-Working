package pitayaa.nail.domain.salon.elements;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class SalonDetail {

	private String ownerName;
	private String businessName;
	private String website;
	private int totalBranch;

	@ElementCollection
	private List<String> branch;
}
