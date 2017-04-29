package pitayaa.nail.domain.notification.sms;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import pitayaa.nail.domain.notification.hibernate.transaction.ObjectListener;

@Entity
@Data
@EntityListeners(ObjectListener.class)
public class SmsReceive {
	
	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	@Version
	Long version;
	
	private String salonId;
	private String smsType;
	
	private String fromPhone;
	private String toPhone;
	private String message;
	private String messageId;
	private String status;
	
	private Date createdDate;
	private Date updatedDate;

}
