package pitayaa.nail.json.account.elements;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
import pitayaa.nail.json.license.JsonLicense;

@Data
public class JsonAccountLicense {

	private UUID uuid;

	private JsonLicense license;

	private Date activedDate;

	private Date expiredDate;

	private Date createdDate;

	private Date updatedDate;

	private Boolean isPay;

	private String payType;

}
