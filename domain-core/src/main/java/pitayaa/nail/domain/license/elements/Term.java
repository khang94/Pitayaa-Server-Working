package pitayaa.nail.domain.license.elements;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class Term {

	private String termType; // DAY , MONTH & YEARS

	private int lengthTime;

	private String extendTime;
}
