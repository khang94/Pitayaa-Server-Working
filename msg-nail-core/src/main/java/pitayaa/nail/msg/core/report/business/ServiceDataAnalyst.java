package pitayaa.nail.msg.core.report.business;

import pitayaa.nail.domain.report.elements.ReportServiceData;

public interface ServiceDataAnalyst extends DataAnalyst {

	ReportServiceData getServicesData(String salonId, String from, String to) throws Exception;
	

}
