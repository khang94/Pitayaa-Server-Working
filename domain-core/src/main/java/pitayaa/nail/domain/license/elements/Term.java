package pitayaa.nail.domain.license.elements;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class Term {

	private int termType; // DAY (0), MONTH(1) & YEARS(2) 

	private int lengthTime;

}
