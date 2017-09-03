package pitayaa.nail.domain.report.elements;

import java.util.List;

import lombok.Data;
import pitayaa.nail.domain.report.detail.CustomerData;

@Data
public class ReportCustomerData {

	private List<CustomerData> customersData;
	private Integer totalType;
	private Integer totalCustomers;
	private String salonId;
}
