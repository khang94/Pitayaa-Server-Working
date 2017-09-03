package pitayaa.nail.domain.report.elements;

import java.util.List;

import lombok.Data;
import pitayaa.nail.domain.report.detail.ServiceData;

@Data
public class ReportServiceData {

	private List<ServiceData> servicesData;
	private String salonId;
}
