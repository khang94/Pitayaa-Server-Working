package pitayaa.nail.domain.admin;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import pitayaa.nail.domain.admin.elements.AdminDetail;
import pitayaa.nail.domain.common.Address;
import pitayaa.nail.domain.common.Contact;
import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;

@Data
@Entity
@EntityListeners(ObjectHibernateListener.class)
public class Admin {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	@Version
	Long version;

	@Embedded
	private Contact contact;

	@Embedded
	private AdminDetail adminDetail;

	@Embedded
	private Address address;

	@Temporal(TemporalType.TIMESTAMP)
	private Date registeredDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	private String username;
	private String password;
	private String confirmCode;
	private int type; // 0: admin, 1 agent
	private int status; // 1 active, 0 inactive

}
