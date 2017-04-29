package pitayaa.nail.json.license.elements;

import lombok.Data;

@Data
public class JsonTerm {

	private String termType; // DAY , MONTH & YEARS

	private int lengthTime;

	private String extendTime;
}
