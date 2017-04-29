package pitayaa.nail.domain.notification.sms.elements;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class HeaderSms {

	private String fromPhone;
	private String toPhone;
	private String message;
	private String codeNational;

}
