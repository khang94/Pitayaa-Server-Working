package pitayaa.nail.json.category;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class JsonCategory {

	private UUID uuid;

	private String categoryName;
	private String categoryCode;
	private String viewId;
	private String description;
	private Date createdDate;
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;
}
