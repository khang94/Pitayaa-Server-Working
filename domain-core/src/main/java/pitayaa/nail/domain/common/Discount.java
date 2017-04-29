package pitayaa.nail.domain.common;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Discount {
	
	private String discountType;
	private String discountCode;
	
	private String discountEvent;
	
	private Double discountPercent; 
	private Double originalPrice;
	private Double discountPrice;
	


}
