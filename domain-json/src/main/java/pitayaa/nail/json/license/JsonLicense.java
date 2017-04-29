package pitayaa.nail.json.license;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
import pitayaa.nail.json.license.elements.JsonLicenseDetail;

@Data
public class JsonLicense {

	private UUID uuid;

	private JsonLicenseDetail jsonLicenseDetail;

	private String licenseName;

	private int numEmployee;

	private int numDevices;

	private int numClientProfiles;

	private int numFreeEmail;

	private int numFreeSms;

	private int numShop;

	private boolean isTrial;

	private String timezone;

	private Date createdDate;

	private Date updatedDate;

	private String status;

}
