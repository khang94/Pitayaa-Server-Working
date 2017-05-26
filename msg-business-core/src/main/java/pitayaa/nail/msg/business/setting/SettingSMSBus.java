package pitayaa.nail.msg.business.setting;

import java.util.List;
import java.util.Optional;

import pitayaa.nail.domain.setting.SettingSms;

public interface SettingSMSBus {

	List<SettingSms> getListSettingSMSDefault(String salonId) throws Exception;

}
