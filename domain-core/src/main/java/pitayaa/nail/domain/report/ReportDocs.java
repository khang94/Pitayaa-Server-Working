package pitayaa.nail.domain.report;

import lombok.Data;
import pitayaa.nail.domain.report.elements.ReportPromotionData;
import pitayaa.nail.domain.report.elements.ReportSmsData;

@Data
public class ReportDocs {
	
	Integer totalAppointments;
	Integer totalNoShow;
	
	ReportPromotionData promotionData;
	ReportSmsData smsData;
}
