package pitayaa.nail.domain.report.detail;

import lombok.Data;

@Data
public class PackageData {
	
	String packageName;
	String packageId;
	Integer number;
	Double rate;
	String percentage;
	Integer ranking;
	Integer totalPackages;
}
