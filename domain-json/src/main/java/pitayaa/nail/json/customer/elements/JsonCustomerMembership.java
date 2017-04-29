package pitayaa.nail.json.customer.elements;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class JsonCustomerMembership {

	private UUID uuid;

	private Date registeredDate;

	private Date updatedDate;

	private Date expiredDate;
	private String status;
	private String typeEvent;
	private String promotionCode;
	private String percentDiscount;
	private String note;

}
