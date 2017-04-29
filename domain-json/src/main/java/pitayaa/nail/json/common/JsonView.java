package pitayaa.nail.json.common;

import java.util.UUID;

import lombok.Data;

@Data
public class JsonView {

	private UUID uuid;

	private String moduleId;
	private String color;
	private String colorName;
	private String pathImage;

	private byte[] imgData; // Binary to store image

	private String status; // Visible or Hidden of imgData
	private Boolean isBinaryStored; // to check whether stream have or not
	private String imgType; // Img of color , img of nail , img of background or
							// icon or Avatar

	private String group; // Salon group , customer Group
	private String description; // Discription of image

}
