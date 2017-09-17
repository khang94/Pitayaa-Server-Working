package pitayaa.nail.domain.promotion;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import pitayaa.nail.domain.common.Discount;
import pitayaa.nail.domain.promotion.elements.PackagePromotion;
import pitayaa.nail.domain.promotion.elements.ServicePromotion;

@Entity
@Data
public class PromotionGroup {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	private String promotionName;

	private String event;
	private String message;
	private String description;
	
	private Integer loyaltyPoint;
	
	private String promotionKind;
	
	@Embedded
	private Discount promotionDiscount;

	@OneToMany(cascade = CascadeType.ALL)
	private List<ServicePromotion> servicesPromotion;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<PackagePromotion> packagePromotion;

	private Date expireFrom;
	private Date expireTo;

	private Integer total;
	private Integer totalUsed;
	private Integer totalAvailable;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	private String salonId;

}
