package pitayaa.nail.notification.scheduler.service;

import java.util.List;

import pitayaa.nail.domain.notification.scheduler.SmsQueue;
import pitayaa.nail.domain.notification.sms.SmsModel;
import pitayaa.nail.domain.setting.SettingSms;
import pitayaa.nail.json.http.JsonHttp;

public interface SmsQueueService {

	SmsQueue saveQueue(SmsQueue smsQueue);

	List<SmsQueue> getQueue(String customerId, String settingSmsId, String customerType);

	JsonHttp deliverMessage(SettingSms settingSms) throws Exception;

	SmsQueue getTopQueue(String customerId, String settingSmsId, String customerType);

	List<SmsQueue> getSmsReport(String salonId, String from, String to) throws Exception;

}
