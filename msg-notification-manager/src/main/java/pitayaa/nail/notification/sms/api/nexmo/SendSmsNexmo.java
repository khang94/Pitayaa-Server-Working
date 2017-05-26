package pitayaa.nail.notification.sms.api.nexmo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.notification.sms.SmsModel;
import pitayaa.nail.msg.business.util.security.EncryptionUtils;
import pitayaa.nail.notification.sms.config.SmsConstant;

import com.nexmo.messaging.sdk.NexmoSmsClient;
import com.nexmo.messaging.sdk.SmsSubmissionResult;
import com.nexmo.messaging.sdk.messages.TextMessage;

@Service
public class SendSmsNexmo {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(SendSmsNexmo.class);

	public SmsModel sendSmsAPI(SmsModel smsModel, String apiKey,
			String apiSecret) {

		apiKey = EncryptionUtils.decrypt(apiKey, EncryptionUtils.getKey());
		apiSecret = EncryptionUtils
				.decrypt(apiSecret, EncryptionUtils.getKey());

		LOGGER.info("Decrypt API Key & API Secret Successfully !");
		
		// Test
		//apiKey = "620ba56b";
		//apiSecret = "9debAcc7";
		
		apiKey = "08a5e867";
		apiSecret = "a2ed09b2e0754c45";
		
		//apiKey = "bd49d0df";
		//apiSecret = "f1e525b0f9ecae5f";
		

		NexmoSmsClient client = null;
		try {
			client = new NexmoSmsClient(apiKey, apiSecret);
		} catch (Exception e) {
			LOGGER.info("Failed to instantiate a Nexmo Client");
			e.printStackTrace();
			throw new RuntimeException("Failed to instantiate a Nexmo Client");
		}

		// Create a Text SMS Message request object ...
		String from = smsModel.getHeader().getFromPhone();
		String to = smsModel.getHeader().getToPhone();
		String messageSend = smsModel.getHeader().getMessage();

		
		if(!to.startsWith("1") && !to.startsWith("84")){
			to = "1" + to;
		}
		TextMessage message = new TextMessage(from, to, messageSend);

		// Use the Nexmo client to submit the Text Message ...
		

		SmsSubmissionResult[] results = null;
		try {
			results = client.submitMessage(message);

		} catch (Exception e) {
			LOGGER.info("Failed to communicate with the Nexmo Client");
			e.printStackTrace();
			throw new RuntimeException(
					"Failed to communicate with the Nexmo Client");
		}

		// Evaluate the results of the submission attempt ...
		LOGGER.info("... Message submitted in [ " + results.length + " ] parts");
		for (int i = 0; i < results.length; i++) {
			LOGGER.info("--------- part [ " + (i + 1) + " ] ------------");
			LOGGER.info("Status [ " + results[i].getStatus() + " ] ...");
			if (results[i].getStatus() == SmsSubmissionResult.STATUS_OK) {
				smsModel.getMeta().setStatus(SmsConstant.STATUS_SMS_DELIVERED);
				LOGGER.info("SUCCESS");
			} else if (results[i].getTemporaryError()) {
				smsModel.getMeta().setStatus(
						SmsConstant.STATUS_SMS_TEMPORARY_FAILED);
				LOGGER.info("TEMPORARY FAILURE - PLEASE RETRY");
			} else {
				smsModel.getMeta().setStatus(
						SmsConstant.STATUS_SMS_FAILED_DELIVER);
				LOGGER.info("SUBMISSION FAILED!");
				LOGGER.info("Message-Id [ " + results[i].getMessageId()
						+ " ] ...");
				LOGGER.info("Error-Text [ " + results[i].getErrorText()
						+ " ] ...");
			}
			if (results[i].getMessagePrice() != null) {
				LOGGER.info("Message-Price [ " + results[i].getMessagePrice()
						+ " ] ...");
			}
			if (results[i].getRemainingBalance() != null) {
				LOGGER.info("Remaining-Balance [ "
						+ results[i].getRemainingBalance() + " ] ...");
			}
			smsModel.setMessageId(results[0].getMessageId());
		}
		return smsModel;
	}
}
