package pitayaa.nail.msg.core.promotion.service;

import java.util.HashMap;
import java.util.List;

import pitayaa.nail.domain.promotion.Promotion;

public interface PromotionService {

	boolean generateCode(Promotion codeExpect, int number);

	Promotion save(Promotion promotion);

	HashMap<String, String> verifyPromotionCode(String code, String salonId) throws Exception;

	Promotion getPromotionCodeRandom(String salonId, String type , String customerId) throws Exception;

	Promotion signInPromotion(String salonId, String codeValue) throws Exception;

	Promotion findPromotion(String salonId, String codeValue) throws Exception;

	List<Promotion> findPromotionByConditions(String salonId, String status, String from, String to) throws Exception;

	List<Promotion> findPromotionActive(String salonId) throws Exception;

}
