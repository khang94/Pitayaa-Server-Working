package pitayaa.nail.domain.service;

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

import pitayaa.nail.domain.common.Discount;
import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;
import pitayaa.nail.domain.license.elements.Price;
import pitayaa.nail.domain.view.View;

@Data
@Entity
@EntityListeners(ObjectHibernateListener.class)
public class ServiceModel {
	
	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;
	
	@Version 
	Long version;
	
	private String categoryId;
	private String salonId;
	private String serviceName;
	private String serviceType;
	private String serviceCode;
	
	@Embedded 
	private Discount serviceDiscount;
	
	@OneToOne(cascade = CascadeType.ALL)
	private View view;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;
	
	private String description;
	private String status;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Price price1;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Price price2;
	
	private int duration;

}
