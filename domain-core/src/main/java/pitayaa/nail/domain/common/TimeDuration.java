package pitayaa.nail.domain.common;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class TimeDuration {
	
	private String typeTime; // Hours or minutes
	private String valueTime;
}
