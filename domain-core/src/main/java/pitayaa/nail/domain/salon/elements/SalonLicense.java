package pitayaa.nail.domain.salon.elements;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;
import pitayaa.nail.domain.license.License;
import lombok.Data;

@Data
@Entity
@EntityListeners(ObjectHibernateListener.class)
public class SalonLicense {
	
	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	@OneToOne(cascade = CascadeType.ALL)
	private License license;

	@Temporal(TemporalType.TIMESTAMP)
	private Date activeDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredDate;

	private Date createdDate;

	private Date updatedDate;
	
	private Boolean isPay;
	
	private String payType;
}
