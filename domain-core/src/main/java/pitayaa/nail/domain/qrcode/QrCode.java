package pitayaa.nail.domain.qrcode;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;

@Entity
@Data
@EntityListeners(ObjectHibernateListener.class)
public class QrCode {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;
	
	private String value;
	private String salonId;
	private String moduleId; // Customer ID or Employee ID
	private String type; // QR OR BARCODE
	private String userType; // EMPLOYEE or CUSTOMER

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
}
