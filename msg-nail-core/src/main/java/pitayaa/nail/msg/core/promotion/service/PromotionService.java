package pitayaa.nail.msg.core.promotion.service;

import java.util.HashMap;

import pitayaa.nail.domain.promotion.Promotion;

public interface PromotionService {

	boolean generateCode(Promotion codeExpect, int number);

	Promotion save(Promotion promotion);

	HashMap<String, String> verifyPromotionCode(String code, String salonId) throws Exception;

	Promotion getPromotionCodeRandom(String salonId, String type , String customerId) throws Exception;

	Promotion signInPromotion(String salonId, String codeValue) throws Exception;

	Promotion findPromotion(String salonId, String codeValue) throws Exception;

}
