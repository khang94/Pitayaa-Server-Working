package pitayaa.nail.domain.promotion.elements;

import javax.persistence.Embeddable;

@Embeddable
public class PromotionSummary {

	private Integer total;
	private Integer totalUsed;
	private Integer totalAvailable;
}
