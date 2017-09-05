package pitayaa.nail.domain.customer;

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

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import pitayaa.nail.domain.common.Address;
import pitayaa.nail.domain.common.Contact;
import pitayaa.nail.domain.customer.elements.CustomerDetail;
import pitayaa.nail.domain.customer.elements.CustomerMembership;
import pitayaa.nail.domain.customer.elements.CustomerPayment;
import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;
import pitayaa.nail.domain.view.View;

@Data
@Entity
@EntityListeners(ObjectHibernateListener.class)
public class Customer {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	@Embedded
	private CustomerDetail customerDetail;

	@Embedded
	private Contact contact;

	@Embedded
	private Address address;
	
	@OneToOne(cascade = CascadeType.ALL)
	private View view;

	@OneToOne(cascade = CascadeType.ALL)
	private CustomerPayment customerPayment;

	@OneToOne(cascade = CascadeType.ALL)
	private CustomerMembership customerMembership;

	private String username;
	private String password;
	
	private String referralBy;
	private String referralById;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;
	private String salonId;
	private String qrCode;

}
