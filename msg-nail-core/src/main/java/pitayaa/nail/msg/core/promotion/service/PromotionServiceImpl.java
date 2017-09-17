package pitayaa.nail.msg.core.promotion.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.promotion.Promotion;
import pitayaa.nail.domain.promotion.PromotionGroup;
import pitayaa.nail.msg.core.common.CoreConstant;
import pitayaa.nail.msg.core.common.TimeUtils;
import pitayaa.nail.msg.core.promotion.repository.PromotionGroupRepository;
import pitayaa.nail.msg.core.promotion.repository.PromotionRepository;

@Service
public class PromotionServiceImpl implements PromotionService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionServiceImpl.class);

	@Autowired
	public PromotionServiceHelper serviceHelper;

	@Autowired
	public PromotionRepository promotionRepo;
	
	@Autowired
	public PromotionGroupRepository promotionGroupRepo;
	
	@Override
	public List<PromotionGroup> getPromotionGroupBySalonId(String salonId) throws Exception {
		
		return promotionGroupRepo.findBySalonId(salonId);
	}
	
	@Override
	public PromotionGroup createPromotionGroup(PromotionGroup pg) throws Exception{
		
		pg = promotionGroupRepo.save(pg);
		
		// Build promotion to generate
		Promotion promotion = this.buildCodeForGroup(pg);
		boolean isGenerateSuccess = this.generateCode(promotion, pg.getTotal());
		
		String message = (isGenerateSuccess) ? "Generate promotion code success " : "Generate promotion code failed....";
		LOGGER.info(message);
		
		return pg;
	}
	
	@Override
	public PromotionGroup updatePromotionGroup(UUID uid , PromotionGroup pg) throws Exception{
		
		pg.setUuid(uid);
		pg = promotionGroupRepo.save(pg);
		
		// Build promotion to generate
		Promotion promotion = this.buildCodeForGroup(pg);
		boolean isGenerateSuccess = this.generateCode(promotion, pg.getTotal());
		
		String message = (isGenerateSuccess) ? "Generate promotion code success " : "Generate promotion code failed....";
		LOGGER.info(message);
		
		return pg;
	}
	
	private Promotion buildCodeForGroup(PromotionGroup pg){
		Promotion promotion = new Promotion();
		
		// Basic info
		promotion.setExpireFrom(pg.getExpireFrom());
		promotion.setExpireTo(pg.getExpireTo());
		promotion.setMessage(pg.getMessage());
		promotion.setStatus(CoreConstant.PROMOTION_CODE_ACTIVE);
		promotion.setSalonId(pg.getSalonId());
		promotion.setPromotionDiscount(pg.getPromotionDiscount());
		promotion.setGroupId(pg.getUuid().toString());
		
		return promotion;
	}

	@Override
	public boolean generateCode(Promotion codeExpect, int number) {
		
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
	public List<Promotion> findPromotionActive(String salonId) throws Exception {
		return promotionRepo.findActivePromotionCode(salonId, CoreConstant.PROMOTION_CODE_ACTIVE);
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
	
	@Override
	public List<Promotion> findPromotionByConditions(String salonId ,String status,String from , String to) throws Exception{
		
		// Get convert string to date
		Date fromDate = TimeUtils.getStartDate(from);
		Date toDate = TimeUtils.getEndDate(to);
		
		return promotionRepo.findPromotionByConditions(salonId, status, fromDate, toDate);
	}

}
