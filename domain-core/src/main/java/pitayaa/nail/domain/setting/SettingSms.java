package pitayaa.nail.domain.setting;

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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import pitayaa.nail.domain.hibernate.transaction.ObjectHibernateListener;
import pitayaa.nail.domain.setting.sms.CustomerGroupSending;
import pitayaa.nail.domain.setting.sms.EmployeeGroupSending;
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
	private String repeatType; // DAY or HOURS or Time, D
	private Integer timesRepeat;
	private Integer minutesRepeat;
	private Integer hoursRepeat;
	private String content;
	
	@Embedded
	private TemplateDetail templateDetail;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private CustomerGroupSending customerGroups;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private EmployeeGroupSending employeeGroups;
	
	private boolean autoSend;
	private boolean autoSendEmployee;
	
	private String sendSmsOn;
	private String sendSmsOnTime;

	private String note;
	private String holidayType;

	private String sendSmsToGroup;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	private String updatedBy;
	private String salonId;
}
