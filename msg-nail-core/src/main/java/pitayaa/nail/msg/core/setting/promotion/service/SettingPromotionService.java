package pitayaa.nail.msg.core.setting.promotion.service;

import java.util.Optional;
import java.util.UUID;

import pitayaa.nail.domain.setting.SettingPromotion;

public interface SettingPromotionService {

	SettingPromotion initModel() throws Exception;

	SettingPromotion save(SettingPromotion settingPromote);

	Optional<SettingPromotion> findOne(UUID uid);

	SettingPromotion getSettingPromoteBySalonId(String salonId);

	SettingPromotion update(SettingPromotion settingPromote, SettingPromotion settingUpdated);

}
