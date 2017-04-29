package pitayaa.nail.domain.notification.scheduler;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import pitayaa.nail.domain.notification.common.Metadata;
import pitayaa.nail.domain.notification.email.EmailModel;
import pitayaa.nail.domain.notification.email.elements.Attachment;
import pitayaa.nail.domain.notification.email.elements.HeaderEmail;
import pitayaa.nail.domain.notification.hibernate.transaction.ObjectListener;

@Data
@Entity
@EntityListeners(ObjectListener.class)
public class SmsQueue {
	
	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	@Version
	Long version;
	
	public String customerId;
	public String settingSmsId;
	
	private Date sendTime;
	private Boolean isSend;
	
	private String smsType;
	private String customerType;
	
	private Date timeUpdateSetting;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
	private String salonId;;

}
