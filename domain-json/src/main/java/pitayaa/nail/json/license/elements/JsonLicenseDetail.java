package pitayaa.nail.json.license.elements;

import java.util.UUID;

import lombok.Data;

@Data
public class JsonLicenseDetail {

	private UUID uuid;

	private JsonPrice licensePrice;

	private JsonTerm licenseTerm; // Detail of Term

	private String licenseType; // For salon , customer or account

	private String note;
}
