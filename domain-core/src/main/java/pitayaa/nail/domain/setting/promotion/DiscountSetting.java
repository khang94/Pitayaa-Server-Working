package pitayaa.nail.domain.setting.promotion;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;

@Data
@Entity
public class DiscountSetting {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;
	
	@Version 
	Long version;
	
	private String discountType;
	private String discountLabel;
	private Double  discountPercent;
	private Integer loyaltyPoint;
	
}
