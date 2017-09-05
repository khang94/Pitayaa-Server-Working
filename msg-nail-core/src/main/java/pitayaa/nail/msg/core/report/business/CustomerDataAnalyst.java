package pitayaa.nail.msg.core.report.business;

import java.util.Date;

import pitayaa.nail.domain.report.elements.ReportCustomerData;

public interface CustomerDataAnalyst extends DataAnalyst {

	ReportCustomerData getCustomersDataByTime(ReportCustomerData customerData, String salonId, Date from, Date to)
			throws Exception;

	ReportCustomerData getCustomersDataAll(ReportCustomerData customerData, String salonId);

	ReportCustomerData getCustomersData(String salonId, String from, String to) throws Exception;

}
