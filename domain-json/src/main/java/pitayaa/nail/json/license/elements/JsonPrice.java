package pitayaa.nail.json.license.elements;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class JsonPrice {

	private UUID uuid;

	private String priceType;
	private Double price;

	private Date createDate;

	private Date updateDate;
}
