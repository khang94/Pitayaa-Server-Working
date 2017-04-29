package pitayaa.nail.json.common;

import lombok.Data;

@Data
public class JsonDiscount {

	private String discountType;
	private String discountCode;

	private String discountEvent;

	private Double discountPercent;
	private Double originalPrice;
	private Double discountPrice;

}
