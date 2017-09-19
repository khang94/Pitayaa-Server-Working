package pitayaa.nail.msg.core.promotion.service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import pitayaa.nail.domain.promotion.Promotion;
import pitayaa.nail.domain.promotion.PromotionDataSms;
import pitayaa.nail.domain.promotion.PromotionGroup;

public interface PromotionService {

	boolean generateCode(Promotion codeExpect, int number);

	Promotion save(Promotion promotion);

	HashMap<String, String> verifyPromotionCode(String code, String salonId) throws Exception;

	Promotion getPromotionCodeRandom(String salonId, String type , String customerId) throws Exception;

	Promotion signInPromotion(String salonId, String codeValue) throws Exception;

	Promotion findPromotion(String salonId, String codeValue) throws Exception;

	List<Promotion> findPromotionByConditions(String salonId, String status, String from, String to) throws Exception;

	List<Promotion> findPromotionActive(String salonId) throws Exception;

	PromotionGroup createPromotionGroup(PromotionGroup pg) throws Exception;

	List<PromotionGroup> getPromotionGroupBySalonId(String salonId) throws Exception;

	PromotionGroup updatePromotionGroup(UUID uid, PromotionGroup pg) throws Exception;

	PromotionDataSms buildPromotionData(PromotionDataSms promotionData) throws Exception;

}
