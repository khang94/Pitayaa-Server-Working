package pitayaa.nail.json.salon.elements;

import java.util.List;

import lombok.Data;

@Data
public class JsonSalonDetail {

	private String ownerName;
	private String businessName;
	private String website;
	private int totalBranch;

	private List<String> branch;
}
