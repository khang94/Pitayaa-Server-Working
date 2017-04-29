package pitayaa.nail.domain.notification.email.elements;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.Data;
import pitayaa.nail.domain.notification.common.KeyValueModel;

@Data
@Embeddable
public class HeaderEmail {

	@Embedded
	private KeyValueModel from;

	@Embedded
	private KeyValueModel to;
	
	@ElementCollection
	private List<KeyValueModel> toReceiver;

	@ElementCollection
	private List<KeyValueModel> cc;
	
	@ElementCollection
	private List<KeyValueModel> bcc;
	
	@Embedded
	private KeyValueModel replyTo;
	private String subject;
	private String bodyText;
	private boolean isEncrypt;
	

}
