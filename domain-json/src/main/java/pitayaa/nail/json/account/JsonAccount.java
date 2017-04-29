package pitayaa.nail.json.account;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import pitayaa.nail.json.account.elements.JsonAccountDetail;
import pitayaa.nail.json.common.JsonAddress;
import pitayaa.nail.json.common.JsonContact;
import pitayaa.nail.json.salon.JsonSalon;

@Data
public class JsonAccount {

	private UUID uuid;

	private JsonAccountDetail accountDetail;

	private JsonContact contact;

	private JsonAddress address;

	private Date registeredDate;

	private Date updatedDate;

	private String username;
	private String password;
	private String confirmCode;

	// Map with Salon
	private List<JsonSalon> salon;

}
