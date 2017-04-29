package pitayaa.nail.domain.appointment.elements;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Data
@Embeddable
public class NotificationSignal {
	
	private boolean isSend;
	private boolean isReply;
	
	@Generated(GenerationTime.ALWAYS)
	@Column(insertable = false , updatable = false , columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendTime;
	
	private Date responseTime;
	private String type; // EMPLOYEE || CUSTOMER
	private String signalStatus;
}
