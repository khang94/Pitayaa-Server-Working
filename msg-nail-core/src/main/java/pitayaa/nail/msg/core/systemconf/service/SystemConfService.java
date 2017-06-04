package pitayaa.nail.msg.core.systemconf.service;

import java.util.Optional;
import java.util.UUID;

import pitayaa.nail.domain.systemconf.SystemConf;

public interface SystemConfService {
	Optional<SystemConf> findOne(UUID id);
	Optional<SystemConf> findModelBy(String salonId,String type,String key)  throws Exception;
	SystemConf save(SystemConf  model) throws Exception;
}
