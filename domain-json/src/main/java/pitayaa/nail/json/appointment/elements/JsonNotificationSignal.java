package pitayaa.nail.json.appointment.elements;

import java.util.Date;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
public class JsonNotificationSignal {
	
	private boolean isSend;
	private boolean isReply;
	private Date responseTime;
	private String type; // EMPLOYEE || CUSTOMER
	private String signalStatus;
}
