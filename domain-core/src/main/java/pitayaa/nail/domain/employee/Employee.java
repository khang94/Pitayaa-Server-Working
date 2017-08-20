package pitayaa.nail.domain.employee;

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
import pitayaa.nail.domain.employee.elements.EmployeeDetail;
import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;
import pitayaa.nail.domain.view.View;

@Data
@Entity
@EntityListeners(ObjectHibernateListener.class)
public class Employee {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	@Embedded
	private EmployeeDetail employeeDetail;

	@Embedded
	private Address address;

	@Embedded
	private Contact contact;

	@OneToOne(cascade = CascadeType.ALL)
	private View view;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	private String additionalInfor;
	
	private String shortName;
	
	private String password;//must 4 numbers
	private String qrCode;
	private String salonId;

}
