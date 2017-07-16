package pitayaa.nail.msg.business.setting.promotion;

import pitayaa.nail.domain.setting.SettingPromotion;

public interface SettingPromotionBus {

	SettingPromotion getSettingPromotionDefault(String salonId) throws Exception;

}
