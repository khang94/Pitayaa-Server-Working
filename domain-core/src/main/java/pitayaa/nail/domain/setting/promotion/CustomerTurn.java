package pitayaa.nail.domain.setting.promotion;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;

@Data
@Entity
@EntityListeners(ObjectHibernateListener.class)
public class CustomerTurn {
	
	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;
	
	private Integer timeTurns;
	private Integer period; 
	private String periodType; // Week, Month, Quarter , Half of Year , Years
	private String targetType; // REGULAR , VIP Customer
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	private String updatedBy;
	private String salonId;
	
}
