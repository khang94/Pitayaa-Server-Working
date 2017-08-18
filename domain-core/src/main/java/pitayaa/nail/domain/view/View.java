package pitayaa.nail.domain.view;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;

@Entity
@Data
@EntityListeners(ObjectHibernateListener.class)
public class View {
	
	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	private String moduleId;
	private String color;
	private String colorName;
	private String pathImage;
	
	private String fileName;
	private String extension;
	private String size;

	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(length=16777215)
	private byte[] imgData; // Binary to store image
	
	private String status; // Visible or Hidden of imgData
	private Boolean isBinaryStored; // to check whether stream have or not
	private Integer imgType; // Img of color , img of nail , img of background or icon or Avatar
	private String pathImageServer;
	
	
	private String type; // Salon group , customer Group 
	private String description; // Discription of image
	private String salonId;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
	
}
