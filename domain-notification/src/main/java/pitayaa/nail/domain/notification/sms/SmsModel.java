package pitayaa.nail.domain.notification.sms;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import pitayaa.nail.domain.notification.common.KeyValueModel;
import pitayaa.nail.domain.notification.common.Metadata;
import pitayaa.nail.domain.notification.hibernate.transaction.ObjectListener;
import pitayaa.nail.domain.notification.sms.elements.HeaderSms;
import pitayaa.nail.domain.notification.sms.elements.InteractionData;

@Data
@Entity
@EntityListeners(ObjectListener.class)
public class SmsModel {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	@Version
	Long version;
	
	private String salonId;
	private String smsType;
	private String moduleId;
	private String messageFor;
	private String messageId;
	
	@ElementCollection
	private List<KeyValueModel> wordBinding;	
	
	@Embedded
	private HeaderSms header;
	
	@Embedded
	private Metadata meta;
	
	@OneToOne(cascade = CascadeType.ALL)
	private InteractionData interactionData;
	
	

}
