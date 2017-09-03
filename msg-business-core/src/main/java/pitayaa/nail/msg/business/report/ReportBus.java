package pitayaa.nail.msg.business.report;

import pitayaa.nail.domain.report.elements.ReportCustomerData;

public interface ReportBus {

	ReportCustomerData initCustomerData() throws Exception;

}
