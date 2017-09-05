package pitayaa.nail.domain.report.elements;

import java.util.List;

import lombok.Data;
import pitayaa.nail.domain.report.detail.PromotionData;

@Data
public class ReportPromotionData {
	
	private List<PromotionData> promotionsData;
	private Integer total;
	private Integer totalAvailable;
	private Integer totalUsed;
	private Integer totalSentOut;
	private Integer totalRemained;
	private String salonId;
}
