package pitayaa.nail.json.salon;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
import pitayaa.nail.json.common.JsonAddress;
import pitayaa.nail.json.common.JsonContact;
import pitayaa.nail.json.salon.elements.JsonSalonDetail;

@Data
public class JsonSalon {

	private UUID uuid;

	private JsonSalonDetail salonDetail;

	private JsonAddress address;

	private JsonContact contact;

	private String viewId;
	private String salonCode;
	private String accountId;

	private Date createdDate;

	private Date updatedDate;
	private String createdBy;
	private String updatedBy;

	private String status;
	private String description;
}
