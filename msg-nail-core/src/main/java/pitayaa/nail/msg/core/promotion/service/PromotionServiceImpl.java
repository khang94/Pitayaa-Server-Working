package pitayaa.nail.msg.core.promotion.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.base.KeyValue;
import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.promotion.Promotion;
import pitayaa.nail.domain.promotion.PromotionDataSms;
import pitayaa.nail.domain.promotion.PromotionGroup;
import pitayaa.nail.domain.salon.Salon;
import pitayaa.nail.msg.core.common.CoreConstant;
import pitayaa.nail.msg.core.common.TimeUtils;
import pitayaa.nail.msg.core.customer.service.CustomerService;
import pitayaa.nail.msg.core.promotion.repository.PromotionGroupRepository;
import pitayaa.nail.msg.core.promotion.repository.PromotionRepository;
import pitayaa.nail.msg.core.salon.service.SalonService;

@Service
public class PromotionServiceImpl implements PromotionService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionServiceImpl.class);

	@Autowired
	public PromotionServiceHelper serviceHelper;

	@Autowired
	public PromotionRepository promotionRepo;
	
	@Autowired
	public PromotionGroupRepository promotionGroupRepo;
	
	@Autowired 
	public SalonService salonService;
	
	@Autowired
	private CustomerService customerService;
	
	@Override
	public List<PromotionGroup> getPromotionGroupBySalonId(String salonId) throws Exception {
		
		return promotionGroupRepo.findBySalonId(salonId);
	}
	
	@Override
	public PromotionGroup createPromotionGroup(PromotionGroup pg) throws Exception{
		
		pg.setTotalAvailable(pg.getTotal());
		pg.setTotalUsed(0);
		
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
		
		// Get total number generate
		int number = pg.getTotal();
		
		pg.setUuid(uid);
		pg = this.updatePromotionGroupData(pg);

		
		// Build promotion to generate
		Promotion promotion = this.buildCodeForGroup(pg);
		boolean isGenerateSuccess = this.generateCode(promotion, number);
		
		
		String message = (isGenerateSuccess) ? "Generate promotion code success " : "Generate promotion code failed....";
		LOGGER.info(message);
		
		return pg;
	}
	
	private PromotionGroup updatePromotionGroupData(PromotionGroup promotionGroup) throws Exception {
		
 		List<Promotion> promotions = promotionRepo.findPromotionByGroup(promotionGroup.getSalonId(), promotionGroup.getUuid().toString());
		
		List<Promotion> promotionAvailable = promotionRepo.findPromotionByGroupAndStatus(promotionGroup.getSalonId(),
					promotionGroup.getUuid().toString(), CoreConstant.PROMOTION_CODE_ACTIVE);
		
		int total = promotionGroup.getTotal();
		
		promotionGroup.setTotal(total + promotions.size());
		promotionGroup.setTotalUsed(promotions.size() - promotionAvailable.size());
		promotionGroup.setTotalAvailable(promotionAvailable.size() + total);
		
		promotionGroup = promotionGroupRepo.save(promotionGroup);
		
		return promotionGroup;
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
					message = p.getMessage();
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
	
	@Override
	public PromotionDataSms buildPromotionData(PromotionDataSms promotionData) throws Exception {
		
		if(CoreConstant.KEYWORD_PROMOTION_CODE.equalsIgnoreCase(promotionData.getPromoKeyValue().getPromotionKey())){
			this.getFullyPromotionData(promotionData);
		}		
		this.getFullySalonData(promotionData);
		this.getFullyCustomerData(promotionData);
		
		return promotionData;
	}
	
	private PromotionDataSms getFullySalonData(PromotionDataSms promotionData) throws Exception {
		
		Optional<Salon> salonData = salonService.findOne(UUID.fromString(promotionData.getSalonId()));
		if(salonData.isPresent()){
			for(KeyValue keyValue : promotionData.getKeyValues()){
				if(CoreConstant.KEYWORD_SALON_NAME.equalsIgnoreCase(keyValue.getKey())){
					keyValue.setValue(salonData.get().getSalonDetail().getBusinessName());
					continue;
				}
				if(CoreConstant.KEYWORD_SALON_EMAIL.equalsIgnoreCase(keyValue.getKey())){
					keyValue.setValue(salonData.get().getContact().getEmail());
					continue;
				}
				if(CoreConstant.KEYWORD_SALON_PHONE.equalsIgnoreCase(keyValue.getKey())){
					keyValue.setValue(salonData.get().getContact().getMobilePhone());
					continue;
				}
			}
		}
		return promotionData;
	}
	
	private PromotionDataSms getFullyPromotionData(PromotionDataSms promotionData) throws Exception {
		
		List<Promotion> promotions = promotionRepo.findPromotionByGroupAndStatus(promotionData.getSalonId(), promotionData.getPromoKeyValue().getGroupId() , CoreConstant.PROMOTION_CODE_ACTIVE);
		Promotion promotion = promotions.get(0);
		
		// DELIVER Promotion
		this.deliverPromotionCode(promotion, promotionData ,promotionData.getPromoKeyValue().getGroupId());
		
		promotionData.getPromoKeyValue().setPromotionCode(promotion.getCodeValue());
		promotionData.getPromoKeyValue().setMessage(promotion.getMessage());
		
		return promotionData;
		
	}
	
	private Promotion deliverPromotionCode(Promotion promotionDeliver , PromotionDataSms promotionData , String promotionGroupId){
		

		
		// Update status
		promotionDeliver.setStatus(CoreConstant.PROMOTION_CODE_SEND_OUT);
		
		// Update objective
		promotionDeliver.setCustomerId(promotionData.getCustomerId());
		
		// Update promotion
		promotionDeliver = promotionRepo.save(promotionDeliver);
		
		// Update promotion group
		PromotionGroup pg = promotionGroupRepo.findOne(UUID.fromString(promotionGroupId));
		
		pg.setTotalUsed(pg.getTotalUsed() + 1);
		pg.setTotalAvailable(pg.getTotalAvailable() - 1);
		
		return promotionDeliver;
	}
	
	private PromotionDataSms getFullyCustomerData(PromotionDataSms promotionData) throws Exception {
		
		Optional<Customer> customer = customerService.findOne(UUID.fromString(promotionData.getCustomerId()));
		
		for(KeyValue keyValue : promotionData.getKeyValues()){
			if(CoreConstant.KEYWORD_CUSTOMER_NAME.equalsIgnoreCase(keyValue.getKey()) && customer.isPresent()){
				keyValue.setValue(customer.get().getCustomerDetail().getFirstName() + " " 
						+ customer.get().getCustomerDetail().getLastName());
				break;
			}
		}
		
		return promotionData;
		
	}
}
