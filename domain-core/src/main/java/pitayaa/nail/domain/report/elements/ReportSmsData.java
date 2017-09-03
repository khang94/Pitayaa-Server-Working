package pitayaa.nail.domain.report.elements;

import lombok.Data;

@Data
public class ReportSmsData {
	
	private Integer total;
	private Integer totalSent;
	private Integer totalRemain;
	private String salonId;
}
