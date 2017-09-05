package pitayaa.nail.msg.core.integration;

import java.util.List;

import pitayaa.nail.domain.notification.scheduler.SmsQueue;

public interface IntegrationService {

	List<SmsQueue> getReportSms(String salonId, String from, String to) throws Exception;

}
