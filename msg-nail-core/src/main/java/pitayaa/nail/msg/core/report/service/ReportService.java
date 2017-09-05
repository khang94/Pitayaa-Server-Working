package pitayaa.nail.msg.core.report.service;

import pitayaa.nail.domain.report.ReportDocs;
import pitayaa.nail.domain.report.elements.ReportCustomerData;
import pitayaa.nail.domain.report.elements.ReportServiceData;

public interface ReportService {

	ReportCustomerData getCustomersData(String salonId,String from , String to) throws Exception;

	ReportServiceData getServicesData(String salonId, String from, String to) throws Exception;

	ReportDocs getPromotionReport(String salonId, String from, String to) throws Exception;

}
