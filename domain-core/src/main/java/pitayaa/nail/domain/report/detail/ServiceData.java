package pitayaa.nail.domain.report.detail;

import lombok.Data;

@Data
public class ServiceData {
	
	String serviceName;
	String serviceId;
	Integer number;
	Double rate;
	Double percentage;
	Integer ranking;
}
