package pitayaa.nail.domain.common;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Discount {
	
	private String discountCode;
		
	private Double discountPercent; 
	private Double originalPrice;
	private Double discountPrice;
}
