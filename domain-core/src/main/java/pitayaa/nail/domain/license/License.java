package pitayaa.nail.domain.license;

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

import lombok.Data;
import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;
import pitayaa.nail.domain.license.elements.LicenseDetail;

@Data
@Entity
@EntityListeners(ObjectHibernateListener.class)
public class License {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	@OneToOne(cascade = CascadeType.ALL)
	private LicenseDetail licenseDetail;

	private String licenseName;

	private int numEmployee;

	private int numDevices;

	private int numClientProfiles;

	private int numFreeEmail;

	private int numFreeSms;

	private int numShop;

	private boolean isTrial;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	private int status; // 0 not enable, 1 enable

}
