package pitayaa.nail.notification.scheduler.service;

import java.util.List;

import pitayaa.nail.domain.notification.scheduler.SmsQueue;

public interface SmsQueueService {

	SmsQueue saveQueue(SmsQueue smsQueue);

	List<SmsQueue> getQueue(String customerId, String settingSmsId, String customerType);

}
