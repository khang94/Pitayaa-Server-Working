package pitayaa.nail.json.account.elements;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class JsonAccountLicenseHistory {

	private UUID uuid;

	private String accountId;

	private List<String> listLicenseIdUsed;

}
