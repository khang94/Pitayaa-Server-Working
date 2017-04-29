package pitayaa.nail.domain.salon;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import pitayaa.nail.domain.common.Address;
import pitayaa.nail.domain.common.Contact;
import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;
import pitayaa.nail.domain.salon.elements.SalonDetail;
import pitayaa.nail.domain.salon.elements.SalonLicense;
import pitayaa.nail.domain.view.View;

@Data
@Entity
@EntityListeners(ObjectHibernateListener.class)
public class Salon {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	@Version
	Long version;

	@Embedded
	private SalonDetail salonDetail;

	@Embedded
	private Address address;

	@Embedded
	private Contact contact;

	@OneToOne(cascade = CascadeType.ALL)
	private SalonLicense salonLicense;

	@OneToOne(cascade = CascadeType.ALL)
	private View view;
	
	private String salonCode;
	private String accountId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;

	private String status;
	private String description;
}
