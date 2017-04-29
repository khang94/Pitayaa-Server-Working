package pitayaa.nail.json.service;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
import pitayaa.nail.json.common.JsonDiscount;
import pitayaa.nail.json.license.elements.JsonPrice;

@Data
public class JsonServiceModel {

	private UUID uuid;

	private String categoryId;
	private String shopId;
	private String viewId;
	private String serviceName;
	private String serviceType;
	private String serviceCode;

	private Date createdDate;

	private Date updatedDate;
	private String createdBy;
	private String updatedBy;

	private String description;
	private String status;

	private JsonPrice price;

	private JsonDiscount serviceDiscount;

	private int duration;

}
