package pitayaa.nail.msg.business.setting.systemconf;

import java.util.List;

import pitayaa.nail.domain.systemconf.SystemConf;

public interface SystemConfBus {
	public List<SystemConf> getListSystemConfDefault(String salonId);
}
