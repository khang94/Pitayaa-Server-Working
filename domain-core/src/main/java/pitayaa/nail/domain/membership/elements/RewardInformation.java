package pitayaa.nail.domain.membership.elements;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;

@Entity
@Data
@EntityListeners(ObjectHibernateListener.class)
public class RewardInformation {
	
	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;
	
	private Integer totalRewards;
	private Integer usedRewards;
	private Integer availableRewards;
	
	private Double totalEquivalentCash;
	private Double totalSpending;
	private Double lastSpending;
	
	
}
