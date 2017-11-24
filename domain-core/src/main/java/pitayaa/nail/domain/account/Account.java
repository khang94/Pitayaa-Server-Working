package pitayaa.nail.domain.account;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import pitayaa.nail.domain.account.elements.AccountDetail;
import pitayaa.nail.domain.account.elements.AccountLicense;
import pitayaa.nail.domain.common.Address;
import pitayaa.nail.domain.common.Contact;
import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;
import pitayaa.nail.domain.salon.Salon;

@Data
@Entity
@EntityListeners(ObjectHibernateListener.class)
public class Account {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	@Embedded
	private AccountDetail accountDetail;

	@Embedded
	private Contact contact;

	@Embedded
	private Address address;

	@Temporal(TemporalType.TIMESTAMP)
	private Date registeredDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	private String username;
	private String password;
	private String confirmCode;

	// Account License

//	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//	private AccountLicense accountLicense;

	// Map with Salon
	@OneToMany(cascade = CascadeType.ALL)
	private List<Salon> salon;

}
