package pitayaa.nail.domain.report.elements;

import java.util.List;

import lombok.Data;
import pitayaa.nail.domain.report.detail.PackageData;

@Data
public class ReportPackageData {

	private List<PackageData> packagesData;
	Integer totalPackages;
	private String salonId;
}
