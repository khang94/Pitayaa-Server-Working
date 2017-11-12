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
		
		lstSetting = this.buildSystemConfigPreferences(salonId, lstSetting);
		
		return lstSetting;
	}
	
	@Override
	public List<SystemConf> buildSystemConfigPreferences(String salonId , List<SystemConf> lstSetting){
		
		if(lstSetting.isEmpty()){
			lstSetting = new ArrayList<>();
		}
		
		SystemConf systemConf = new SystemConf();
		systemConf.setKey("TIME_BACK_SCREEN_SAVER");
		systemConf.setType("HOME_SCREEN");
		systemConf.setValue("1");
		systemConf.setSalonId(salonId);
		lstSetting.add(systemConf);
		
		systemConf = new SystemConf();
		systemConf.setKey("MAIN_HOME");
		systemConf.setType("HOME_SCREEN");
		systemConf.setValue("0");
		systemConf.setSalonId(salonId);
		lstSetting.add(systemConf);
		
		return lstSetting;
	}
}
