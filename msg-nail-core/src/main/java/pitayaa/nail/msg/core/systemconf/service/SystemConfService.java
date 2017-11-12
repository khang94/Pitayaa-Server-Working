package pitayaa.nail.msg.core.systemconf.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pitayaa.nail.domain.systemconf.SystemConf;
import pitayaa.nail.domain.view.View;

public interface SystemConfService {
	Optional<SystemConf> findOne(UUID id);

	Optional<SystemConf> findModelBy(String salonId, String type, String key) throws Exception;

	SystemConf save(SystemConf model) throws Exception;

	List<SystemConf> getAllConfigPreferences(String salonId) throws Exception;

	List<SystemConf> updateSystemConfiguration(String salonId, List<SystemConf> systemConfigurations) throws Exception;

	SystemConf uploadImageToSetting(View view, String salonId, SystemConf systemData) throws Exception;
}
