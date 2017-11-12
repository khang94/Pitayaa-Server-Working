package pitayaa.nail.msg.core.setting.promotion.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.setting.SettingPromotion;
import pitayaa.nail.domain.setting.promotion.LoyaltyPoint;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.setting.promotion.repository.SettingPromotionRepository;

@Service
public class SettingPromotionServiceImpl implements SettingPromotionService {
	
	@Autowired
	SettingPromotionRepository settingPromoteRepo;
	
	@Autowired
	CoreHelper coreHelper;
	
	@Override
	public SettingPromotion initModel() throws Exception{
		SettingPromotion settingPromote = (SettingPromotion) coreHelper.createModelStructure(new SettingPromotion());
		return settingPromote;
	}
	
	@Override
	public SettingPromotion save(SettingPromotion settingPromote){
		settingPromote = settingPromoteRepo.save(settingPromote);
		return settingPromote;
	}
	
	@Override
	public SettingPromotion update(SettingPromotion settingPromote , SettingPromotion settingUpdated){
		
		settingUpdated.setUuid(settingPromote.getUuid());
		settingUpdated = settingPromoteRepo.save(settingUpdated);
		return settingPromote;
	}
	
	
	@Override
	public Optional<SettingPromotion> findOne(UUID uid){
		Optional<SettingPromotion> savedSettingPromote = Optional.ofNullable(settingPromoteRepo.findOne(uid));
		return savedSettingPromote;
	}
	
	@Override
	public SettingPromotion getSettingPromoteBySalonId(String salonId){
		SettingPromotion savedSettingPromote = settingPromoteRepo.getSettingPromotion(salonId);
		
		return savedSettingPromote;
	}
	
	@Override
	public Double convertRewardToCash(String salonId , Integer point){
		SettingPromotion savedSettingPromote = settingPromoteRepo.getSettingPromotion(salonId);
		
		LoyaltyPoint loyaltyPoint = savedSettingPromote.getLoyaltyPoint();
		
		Double rate = (Double.valueOf(loyaltyPoint.getMoneyExchange().toString())/
						Double.valueOf(loyaltyPoint.getLoyaltyPoint().toString()));
		return point * rate;
		
	}

}
