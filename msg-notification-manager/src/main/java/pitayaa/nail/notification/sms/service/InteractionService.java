package pitayaa.nail.notification.sms.service;

import pitayaa.nail.domain.notification.sms.SmsModel;
import pitayaa.nail.domain.notification.sms.SmsReceive;
import pitayaa.nail.domain.notification.sms.elements.KeyWordDeliverManagement;

public interface InteractionService {

	SmsModel findSmsByResponseKey(SmsReceive smsReceive) throws Exception;

	SmsModel findSmsByResponseKeyTest(String key) throws Exception;

	SmsModel buildSmsAutoResponseToCustomer(SmsReceive smsReceive) throws Exception;

	boolean isMessageReplyByCustomer(SmsModel smsBody);

	SmsModel findSmsByResponseKeyAndPhoneNumber(SmsReceive smsReceive) throws Exception;

	SmsModel buildKeyDeliver(SmsModel smsBody) throws Exception;

}
