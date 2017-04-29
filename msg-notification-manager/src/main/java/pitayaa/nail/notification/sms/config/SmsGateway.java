package pitayaa.nail.notification.sms.config;

import java.util.Map;

import lombok.Data;

/**
 * @author kmai2 (Khang Mai )
 *
 */

@Data
public class SmsGateway {

	private String host;
	private String port;
	private String gateway;
	private String gatewayName;
	private String phoneNumber;
	private String phoneNumberName;
	private String messageParamName;
	private String typeOfRequest;
	private Map<String, String> params;

}
