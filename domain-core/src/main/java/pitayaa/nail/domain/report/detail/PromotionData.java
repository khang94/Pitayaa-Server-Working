package pitayaa.nail.domain.report.detail;

import lombok.Data;

@Data
public class PromotionData {
	
	String promotionType;
	String promotionId;
	Integer number; // Number of customer using promotion 
	Double rate; // Rating
	String percentage;
	Integer ranking; // Raking
	
}
