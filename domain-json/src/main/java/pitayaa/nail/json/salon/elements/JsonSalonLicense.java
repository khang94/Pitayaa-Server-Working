package pitayaa.nail.json.salon.elements;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
import pitayaa.nail.json.license.JsonLicense;

@Data
public class JsonSalonLicense {

	private UUID uuid;

	private JsonLicense license;

	private Date activeDate;

	private Date expiredDate;

	private Date createdDate;

	private Date updatedDate;

	private Boolean isPay;

	private String payType;
}
