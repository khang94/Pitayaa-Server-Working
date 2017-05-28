package pitayaa.nail.domain.packages;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import pitayaa.nail.domain.common.Discount;
import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;
import pitayaa.nail.domain.license.elements.Price;
import pitayaa.nail.domain.packages.elements.PackageDtl;
import pitayaa.nail.domain.view.View;

@Data
@Entity
@EntityListeners(ObjectHibernateListener.class)
public class PackageModel {
	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	@Version
	Long version;

	private String categoryId;
	private String salonId;
	private String packageName;
	private String packageType;
	private String packageCode;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@OneToOne(cascade = CascadeType.ALL)
	private View view;

	private String createdBy;
	private String updatedBy;
	private Integer duration;
	private String description;
	private String status;

	@Embedded
	private Discount packageDiscount;

	@OneToMany(cascade = CascadeType.ALL)
	private List<PackageDtl> packageDtls;

	@OneToOne(cascade = CascadeType.ALL)
	private Price price;

}
