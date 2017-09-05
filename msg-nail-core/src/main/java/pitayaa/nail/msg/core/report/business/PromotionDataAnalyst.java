package pitayaa.nail.msg.core.report.business;

import java.util.List;

import pitayaa.nail.domain.notification.scheduler.SmsQueue;
import pitayaa.nail.domain.report.ReportDocs;

public interface PromotionDataAnalyst {

	List<SmsQueue> getSmsQueue(String salonId, String from, String to) throws Exception;

	ReportDocs getPromotionData(String salonId, String from, String to) throws Exception;

}
