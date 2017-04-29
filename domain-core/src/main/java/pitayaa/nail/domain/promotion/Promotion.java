package pitayaa.nail.domain.promotion;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import pitayaa.nail.domain.common.Discount;
import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;

@Data
@Entity
@EntityListeners(ObjectHibernateListener.class)
public class Promotion {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	@Version
	Long version;

	private String nameEvent;
	private String codeValue;
	private String codeType; // QR or normal
	private String isEncrypt;
	private Date createdDate;
	private Date updatedDate;
	private String createdBy;
	private String updatedBy;
	
	private Date expireTo;
	private Date expireFrom;
	private String salonId;
	private String objectType;
	private String promotionEvent;

	@Embedded
	private Discount promotionDiscount;

}
