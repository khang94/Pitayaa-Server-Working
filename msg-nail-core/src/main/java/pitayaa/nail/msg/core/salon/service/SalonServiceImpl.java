package pitayaa.nail.msg.core.salon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.salon.Salon;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.salon.repository.SalonRepository;
import pitayaa.nail.msg.core.setting.service.SettingService;
import pitayaa.nail.msg.core.setting.service.SettingServiceImpl;

@Service
public class SalonServiceImpl implements SalonService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SettingServiceImpl.class);

	@Autowired
	SalonRepository salonRepo;

	@Autowired
	CoreHelper coreHelper;

	@Autowired
	SettingService settingService;

	@Autowired
	SalonViewService viewService;

	@Override
	public Optional<Salon> findOne(UUID id) {
		return Optional.ofNullable(salonRepo.findOne(id));
	}

	@Override
	public Salon save(Salon salon) throws Exception {

		byte[] binaryImg = null;
		
		// Get stream image
		if (salon.getView() != null){
			binaryImg = salon.getView().getImgData();
			salon.getView().setImgData(null);	
		}
		
		// Create Salon
		salon = salonRepo.save(salon);

		// Build folder & view Image for salon
		salon = viewService.buildViewByDate(salon , binaryImg);

		return salon;
	}
	
	@Override
	public Salon update(Salon salonUpdate, Salon salonOld) throws Exception {

		// Init stream image
		byte[] binaryImg = null;
		
		salonUpdate.setUuid(salonOld.getUuid());
		
		// Create Salon
		if(salonUpdate.getView().getImgData() != null){
			binaryImg = salonUpdate.getView().getImgData();
			salonUpdate.getView().setImgData(null);
		}
		salonUpdate = salonRepo.save(salonUpdate);

		// Build folder & view Image for salon
		salonUpdate = viewService.buildViewByDate(salonUpdate,binaryImg);

		return salonUpdate;
	}

	@Override
	public Salon initModel() throws Exception {
		Salon salon = (Salon) coreHelper.createModelStructure(new Salon());

		return salon;
	}
	
	@Override
	public List<Salon> getAllSalon() throws Exception{
		Iterable<Salon> salons = salonRepo.findAll();
		List<Salon> salonList = new ArrayList<Salon>();
		salons.forEach(salon ->{
			salonList.add(salon);
		});
		return salonList;
	}

}
