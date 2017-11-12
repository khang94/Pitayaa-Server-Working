package pitayaa.nail.domain.notification.sms.elements;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import pitayaa.nail.domain.notification.hibernate.transaction.ObjectListener;
import pitayaa.nail.domain.notification.sms.SmsReceive;

@Data
@Entity
@EntityListeners(ObjectListener.class)
public class InteractionData {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;
	
	@ElementCollection
	List<String> keyResponseDelivers;
	
	@OneToOne(cascade = CascadeType.ALL)
	private SmsReceive smsReceive;
}
