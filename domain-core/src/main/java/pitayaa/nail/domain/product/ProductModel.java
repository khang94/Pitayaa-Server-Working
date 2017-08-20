package pitayaa.nail.domain.product;

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
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import pitayaa.nail.domain.category.Category;
import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;
import pitayaa.nail.domain.license.elements.Price;

@Data
@Entity
@EntityListeners(ObjectHibernateListener.class)
public class ProductModel {
	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	private String salonId;
	private String productName;
	private String barcode;
	private double commission;
	@OneToOne (cascade=CascadeType.ALL)
	private Category category;

	
	@OneToOne(cascade = CascadeType.ALL)
	private Price price1;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Price price2;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
}