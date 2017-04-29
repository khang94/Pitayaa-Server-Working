package pitayaa.nail.domain.notification.email;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import pitayaa.nail.domain.notification.common.Metadata;
import pitayaa.nail.domain.notification.email.elements.Attachment;
import pitayaa.nail.domain.notification.email.elements.HeaderEmail;
import pitayaa.nail.domain.notification.hibernate.transaction.ObjectListener;

@Data
@Entity
@EntityListeners(ObjectListener.class)
public class EmailModel {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	@Type(type = "pg-uuid")
	private UUID uuid;

	@Version
	Long version;

	private String salonId;
	private String emailType;
	
	@Embedded
	private HeaderEmail headerEmail;
	
	@Embedded
	private Metadata meta;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Attachment> attachments;
	


}
