package pitayaa.nail.msg.core.promotion.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.promotion.Promotion;
import pitayaa.nail.msg.core.common.CoreConstant;
import pitayaa.nail.msg.core.promotion.repository.PromotionRepository;

@Service
public class PromotionServiceImpl implements PromotionService {

	@Autowired
	public PromotionServiceHelper serviceHelper;

	@Autowired
	public PromotionRepository promotionRepo;

	@Override
	public boolean generateCode(Promotion codeExpect, int number) {
		
		codeExpect.setStatus(CoreConstant.PROMOTION_CODE_ACTIVE);
		
		List<Promotion> lstCode = serviceHelper.generateCode(codeExpect, number);
		
		boolean isGenSuccess = false;
		if (lstCode.size() == number) {

			for (Promotion p : lstCode) {
				promotionRepo.save(p);
			}
			isGenSuccess = true;
		}
		return isGenSuccess;
	}

	@Override
	public Promotion save(Promotion promotion) {
		return promotionRepo.save(promotion);
	}
	
	@Override
	public HashMap<String,String> verifyPromotionCode(String code , String salonId) throws Exception{
		
		// Initial Result
		HashMap<String,String> response = new HashMap<String,String>();
		String message = "This promotion code is invalid.";
		
		// Is Code Valid
		Boolean isCodeValid = false;
		
		// Verify
		List<Promotion> promotions = promotionRepo.findPromotionCode(salonId, code);
		
		// Check expired date
		Date currentDay = new Date();
		if(promotions.size() > 0){
			Promotion p = promotions.get(0);
			
			if(CoreConstant.PROMOTION_CODE_SEND_OUT.equalsIgnoreCase(p.getStatus())){
				if(currentDay.after(p.getExpireFrom()) && currentDay.before(p.getExpireTo())){
					isCodeValid = true;
					message = "Promotion code is valided.";
				} else {
					message = "Code has been expired or not active . Cannot use this!";
				}
			} else if (CoreConstant.PROMOTION_CODE_USED.equalsIgnoreCase(p.getStatus())){
				message = "This promotion code has been used . Please refer another...";
			}
		}
		
		// Content
		response.put("message", message);
		response.put("isValid", isCodeValid.toString());
		
		return response;
	}
	
	@Override
	public Promotion findPromotion(String salonId , String codeValue) throws Exception {
		List<Promotion> promotions = promotionRepo.findPromotionCode(salonId, codeValue);
		
		return promotions.get(0);
	}
	
	@Override
	public Promotion getPromotionCodeRandom(String salonId , String type , String customerId) throws Exception {
		
		// Query 
		List<Promotion> promotions = promotionRepo.findActivePromotionCode(salonId, CoreConstant.PROMOTION_CODE_ACTIVE);
		
		// Get promotion random
		Promotion promotionDeliver = promotions.get(0);
		
		// Update status
		promotionDeliver.setStatus(CoreConstant.PROMOTION_CODE_SEND_OUT);
		
		// Update objective
		promotionDeliver.setCustomerId(customerId);
		
		// Update promotion
		promotionDeliver = promotionRepo.save(promotionDeliver);
		
		return promotionDeliver;
		
	}
	
	@Override
	public Promotion signInPromotion(String salonId , String codeValue) throws Exception {
		
		// Query
		List<Promotion> promotions = promotionRepo.findPromotionCode(salonId, codeValue);
		
		// Update promotion
		Promotion p = promotions.get(0);
		p.setStatus(CoreConstant.PROMOTION_CODE_USED);
		
		p = promotionRepo.save(p);
		
		return p;
	}

}
