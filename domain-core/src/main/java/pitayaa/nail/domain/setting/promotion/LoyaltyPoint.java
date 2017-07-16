package pitayaa.nail.domain.setting.promotion;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;

@Data
@Entity
@EntityListeners(ObjectHibernateListener.class)
public class LoyaltyPoint {
	
	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;
	
	private Integer loyaltyPoint;
	private Integer moneyExchange;
	private String unit;
	private Double rate;
	
	private String salonId;
	
}
