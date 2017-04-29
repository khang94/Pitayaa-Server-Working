package pitayaa.nail.notification.sms.api.plivo;

import java.util.LinkedHashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.plivo.helper.api.client.RestAPI;
import com.plivo.helper.api.response.message.MessageResponse;
import com.plivo.helper.exception.PlivoException;

import pitayaa.nail.domain.notification.sms.SmsModel;
import pitayaa.nail.notification.common.NotificationConstant;
import pitayaa.nail.notification.sms.config.SmsConstant;

@Service
public class SendSmsPlivo {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(SendSmsPlivo.class);
	
	
	public boolean sendSmsAPI(SmsModel smsBody, Properties properties) {

		boolean isSend = false;

		String authId = properties.getProperty(SmsConstant.AUTH_ID);
		String authToken = properties.getProperty(SmsConstant.AUTH_TOKEN);
		String version = properties.getProperty(SmsConstant.VERSION_API);
		RestAPI api = new RestAPI(authId, authToken, version);

		String srcPhone = smsBody.getHeader().getFromPhone();
		String dstPhone = smsBody.getHeader().getToPhone();
		String text = smsBody.getHeader().getMessage();
		String host = properties.getProperty(SmsConstant.SMS_HOST);

		LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
		parameters.put(SmsConstant.SOURCE_PHONE, srcPhone); // Sender's phone
															// number with
															// country code
		parameters.put(SmsConstant.DESTINY_PHONE, dstPhone); // Receiver's phone
																// number with
																// country code
		parameters.put(SmsConstant.TEXT, text); // Your SMS text message

		// Send Unicode text
		parameters.put(SmsConstant.URL, host); // The URL to which with the
												// status of the message is sent
		parameters.put(SmsConstant.METHOD, NotificationConstant.POST_REQUEST); // The
																				// method
																				// used
																				// to
																				// call
																				// the
																				// url

		try {
			// Send the message
			MessageResponse msgResponse = api.sendMessage(parameters);

			// Print the response
			LOGGER.info(msgResponse.toString());

			// Print the Api ID
			LOGGER.info("Api ID : " + msgResponse.apiId);

			// Print the Response Message
			LOGGER.info("Message : " + msgResponse.message);

			if (msgResponse.serverCode == 202) {
				// Print the Message UUID
				isSend = true;
				smsBody.getMeta().setStatus(SmsConstant.STATUS_SMS_DELIVERED);
				LOGGER.info("Message UUID : "
						+ msgResponse.messageUuids.get(0).toString());
			} else {
				LOGGER.info(msgResponse.error);
			}
		} catch (PlivoException e) {
			smsBody.getMeta().setStatus(SmsConstant.STATUS_SMS_NOT_DELIVERED);
			System.out.println(e.getLocalizedMessage());
		}
		return isSend;
	}


}
