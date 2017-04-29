package pitayaa.nail.domain.account.elements;

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

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;
import pitayaa.nail.domain.license.License;

@Data
@Entity
@EntityListeners(ObjectHibernateListener.class)
public class AccountLicense {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	@OneToOne(cascade = CascadeType.ALL)
	private License license;

	@Temporal(TemporalType.TIMESTAMP)
	private Date activedDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
	private Boolean isPay;
	
	private String payType;
	
	private String accountId;

}
