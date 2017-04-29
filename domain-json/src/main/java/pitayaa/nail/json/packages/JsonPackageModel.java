package pitayaa.nail.json.packages;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import pitayaa.nail.json.common.JsonDiscount;
import pitayaa.nail.json.license.elements.JsonPrice;
import pitayaa.nail.json.service.JsonServiceModel;

@Data
public class JsonPackageModel {

	private UUID uuid;

	private String categoryId;
	private String shopId;
	private String viewId;
	private String packageName;
	private String packageType;
	private String packageCode;

	private Date createdDate;

	private Date updatedDate;

	private String createdBy;
	private String updatedBy;
	private Integer duration;
	private String description;
	private String status;

	private List<JsonServiceModel> groupServices;

	private JsonPrice price;

	private JsonDiscount packageDiscount;

}
