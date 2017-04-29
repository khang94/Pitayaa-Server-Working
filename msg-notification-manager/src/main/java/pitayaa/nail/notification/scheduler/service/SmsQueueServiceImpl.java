package pitayaa.nail.notification.scheduler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.notification.scheduler.SmsQueue;
import pitayaa.nail.notification.scheduler.repository.SmsQueueRepository;

@Service
public class SmsQueueServiceImpl implements SmsQueueService {

	@Autowired
	SmsQueueRepository queueRepo;

	@Override
	public SmsQueue saveQueue(SmsQueue smsQueue) {

		SmsQueue queue = queueRepo.save(smsQueue);

		return queue;
	}
	
	@Override
	public List<SmsQueue> getQueue(String customerId , String settingSmsId , String customerType){
		return queueRepo.getQueue(customerId, settingSmsId, customerType);
	}
}
