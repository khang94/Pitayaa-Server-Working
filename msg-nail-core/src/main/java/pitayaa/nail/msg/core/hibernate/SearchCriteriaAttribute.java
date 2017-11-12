package pitayaa.nail.msg.core.hibernate;

import lombok.Data;

@Data
public class SearchCriteriaAttribute {

	private String attributePath;
	private String attributeName;
	private String operation; // There 4 operation default ":" is LIKE , = , <, >
	private String value;
	private String parameterType;

}
