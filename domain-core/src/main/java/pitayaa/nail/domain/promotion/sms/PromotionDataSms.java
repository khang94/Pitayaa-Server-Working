package pitayaa.nail.domain.promotion.sms;

import java.util.List;

import lombok.Data;
import pitayaa.nail.domain.base.KeyValueModel;

@Data
public class PromotionDataSms {
	
	
	List<KeyValueModel> keyValues;
	PromotionKeyValue promoKeyValue;
	
	private String customerId;
	private String salonId;

}
