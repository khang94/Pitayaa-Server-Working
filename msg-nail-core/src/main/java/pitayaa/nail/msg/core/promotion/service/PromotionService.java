package pitayaa.nail.msg.core.promotion.service;

import pitayaa.nail.domain.promotion.Promotion;

public interface PromotionService {

	boolean generateCode(Promotion codeExpect, int number);

	Promotion save(Promotion promotion);

}
