package pitayaa.nail.domain.category;

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

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;
import pitayaa.nail.domain.view.View;

@Entity
@Data
@EntityListeners(ObjectHibernateListener.class)
public class Category  {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	@Version
	Long version;

	private String categoryName;
	private String categoryCode;
	private String description;

	@OneToOne(cascade = CascadeType.ALL)
	private View view;



	//@Generated(GenerationTime.ALWAYS)
	//@Column(insertable = false , updatable = false , columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	//@Generated(GenerationTime.ALWAYS)
	//@Column(insertable = false , updatable = true,columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;
	private Integer cateType; // 1 : Service , 2 Package , 3 Product
	private String salonId;

}
