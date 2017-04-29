package pitayaa.nail.msg.core.hibernate;

import java.util.List;

import lombok.Data;

@Data
public class SearchCriteria {
	private String entity;
	private List<SearchCriteriaAttribute> attributes;

}
