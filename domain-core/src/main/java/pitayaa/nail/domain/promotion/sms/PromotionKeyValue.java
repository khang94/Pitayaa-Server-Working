package pitayaa.nail.domain.promotion.sms;

import lombok.Data;

@Data
public class PromotionKeyValue {
	
	private String promotionKey;
	private String groupId;
	private String message;
	private String promotionCode;

}
