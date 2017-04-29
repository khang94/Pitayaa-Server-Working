package pitayaa.nail.domain.hibernate.transaction;

import lombok.Data;

@Data
public class QueryCriteria {
	
	String query;
	String object;
	String description;
}
