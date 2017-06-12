package pitayaa.nail.msg.core.setting.sms.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.setting.SettingSms;
import pitayaa.nail.msg.core.setting.sms.repository.SettingSmsRepository;

@Service
public class SettingSmsServiceImpl implements SettingSmsService {

	@Autowired
	SettingSmsRepository settingSmsRepo;

	@Override
	public Optional<SettingSms> findOne(UUID id) {
		return Optional.ofNullable(settingSmsRepo.findOne(id));
	}

	@Override
	public SettingSms save(SettingSms salon) {
		return settingSmsRepo.save(salon);
	}

	@Override
	public Optional<List<SettingSms>> getListSettingSMS(String salonId) {
		return Optional.ofNullable(settingSmsRepo.getListSetting(salonId));
	}
	
	@Override
	public List<SettingSms> updateListSettingSms(String salonId , List<SettingSms> settingLst){
		
		settingLst.stream().forEach(key ->{
			key.setSalonId(salonId);
			/*if(key.getHoursRepeat() == null){
				key.setHoursRepeat(0);
			}
			if(key.getMinutesRepeat() == null){
				key.setMinutesRepeat(0);
			}
			if(key.getTimesRepeat() == null){
				key.setTimesRepeat(0);
			}*/
			key = settingSmsRepo.save(key);
		});
		
		return settingLst;
	}
}
