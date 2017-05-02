package pitayaa.nail.domain.setting;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Embedded;
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
import pitayaa.nail.domain.setting.sms.TemplateDetail;

@Data
@Entity
@EntityListeners(ObjectHibernateListener.class)
public class SettingSms {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	//@Version
	Long version;

	private String type;
	private String key;
	private String repeatType;
	private Integer timesRepeat;
	private Integer minutesRepeat;
	private Integer hoursRepeat;
	private String content;
	
	@Embedded
	private TemplateDetail templateDetail;
	
	private boolean autoSend;
	private String sendSmsOn;
	private String sendSmsOnTime;

	private String note;
	private String holidayType;

	private String sendSmsTo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	private String updatedBy;
	private String salonId;
}
