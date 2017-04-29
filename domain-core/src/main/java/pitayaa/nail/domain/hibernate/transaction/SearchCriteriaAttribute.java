package pitayaa.nail.domain.hibernate.transaction;

import lombok.Data;

@Data
public class SearchCriteriaAttribute {

	private String attributePath;
	private String attributeName;
	private String operation; // There 4 operation default ":" is LIKE , = , <, >
	private String value;

}
