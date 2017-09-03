package pitayaa.nail.msg.core.report.service;

import pitayaa.nail.domain.report.elements.ReportCustomerData;

public interface ReportService {

	ReportCustomerData getCustomersData(String salonId,String from , String to) throws Exception;

}
