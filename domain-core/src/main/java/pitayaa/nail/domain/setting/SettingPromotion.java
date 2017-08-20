package pitayaa.nail.domain.setting;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;
import pitayaa.nail.domain.setting.promotion.CustomerTurn;
import pitayaa.nail.domain.setting.promotion.DiscountSetting;
import pitayaa.nail.domain.setting.promotion.LoyaltyPoint;

@Data
@Entity
@EntityListeners(ObjectHibernateListener.class)
public class SettingPromotion {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	@OneToMany(cascade = { CascadeType.ALL })
	private List<DiscountSetting> discount;

	private Boolean isPushNotification;

	@OneToMany(cascade = { CascadeType.ALL })
	private List<CustomerTurn> customerTurn;

	private Integer pointPromotionCode;
	private Integer pointReferralCode;
	private Integer pointRegularTurn;

	private String key;
	private String type;

	@OneToOne(cascade = { CascadeType.ALL })
	private LoyaltyPoint loyaltyPoint;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	private String updatedBy;
	private String salonId;
}
