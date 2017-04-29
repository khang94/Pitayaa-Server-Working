package pitayaa.nail.json.promotion;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
import pitayaa.nail.json.common.JsonDiscount;

@Data
public class JsonPromotion {

	private UUID uuid;

	private String nameEvent;
	private String codeValue;
	private String codeType; // QR or normal
	private String isEncrypt;
	private Date createdDate;
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;

	private JsonDiscount promotionDiscount;

}
