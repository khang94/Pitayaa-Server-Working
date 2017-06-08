package pitayaa.nail.msg.business.setting.systemconf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import pitayaa.nail.domain.systemconf.SystemConf;

@Service
public class SystemConfBusImpl implements SystemConfBus{
	@Override
	public List<SystemConf> getListSystemConfDefault(String salonId) {
		// TODO Auto-generated method stub
		List<SystemConf> lstSetting=new ArrayList<>();
				
		SystemConf conf=new SystemConf();
		conf.setKey("PERSONALIZE");
		conf.setType("BACKGROUND");
		conf.setValue("0");
		conf.setSalonId(salonId);
		lstSetting.add(conf);
		//---------------
		conf=new SystemConf();
		conf.setKey("PERSONALIZE");
		conf.setType("LOGO");
		conf.setValue("");
		conf.setSalonId(salonId);
		lstSetting.add(conf);
		//---------------
		conf=new SystemConf();
		conf.setKey("PERSONALIZE");
		conf.setType("THEME");
		conf.setValue("0");
		conf.setSalonId(salonId);
		lstSetting.add(conf);
		//---------------
		return lstSetting;
	}
}
