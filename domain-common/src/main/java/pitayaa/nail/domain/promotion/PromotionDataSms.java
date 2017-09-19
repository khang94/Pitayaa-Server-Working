package pitayaa.nail.domain.promotion;

import java.util.List;

import lombok.Data;
import pitayaa.nail.domain.base.KeyValue;

@Data
public class PromotionDataSms {
	
	
	List<KeyValue> keyValues;
	PromotionKeyValue promoKeyValue;
	
	private String customerId;
	private String salonId;

}
