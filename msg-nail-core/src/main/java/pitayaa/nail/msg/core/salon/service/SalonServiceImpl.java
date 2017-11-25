package pitayaa.nail.msg.core.salon.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.license.License;
import pitayaa.nail.domain.salon.Salon;
import pitayaa.nail.domain.salon.elements.SalonLicense;
import pitayaa.nail.msg.business.util.common.DateUtil;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.license.repository.LicenseRepository;
import pitayaa.nail.msg.core.salon.repository.SalonLicenseRepository;
import pitayaa.nail.msg.core.salon.repository.SalonRepository;
import pitayaa.nail.msg.core.setting.service.SettingService;
import pitayaa.nail.msg.core.setting.service.SettingServiceImpl;

@Service
public class SalonServiceImpl implements SalonService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SettingServiceImpl.class);

	@Autowired
	SalonRepository salonRepo;

	@Autowired
	SalonLicenseRepository salonLicenseRepository;

	@Autowired
	LicenseRepository licenseRepo;

	@Autowired
	CoreHelper coreHelper;

	@Autowired
	SettingService settingService;

	@Autowired
	SalonViewService viewService;

	@Override
	public Optional<Salon> findOne(UUID id) {
		Salon salon = salonRepo.findOne(id);
		return Optional.ofNullable(salon);
	}

	@Override
	public Salon save(Salon salon) throws Exception {

		byte[] binaryImg = null;

		// Get stream image
		if (salon.getView() != null) {
			binaryImg = salon.getView().getImgData();
			salon.getView().setImgData(null);
		}

		// Create Salon
		salon = salonRepo.save(salon);

		// Build folder & view Image for salon
		salon = viewService.buildViewByDate(salon, binaryImg);

		return salon;
	}

	@Override
	public Salon update(Salon salonUpdate, Salon salonOld) throws Exception {

		// Init stream image
		byte[] binaryImg = null;

		salonUpdate.setUuid(salonOld.getUuid());

		// Create Salon
		if (salonUpdate.getView().getImgData() != null) {
			binaryImg = salonUpdate.getView().getImgData();
			salonUpdate.getView().setImgData(null);
		}
		salonUpdate = salonRepo.save(salonUpdate);

		// Build folder & view Image for salon
		salonUpdate = viewService.buildViewByDate(salonUpdate, binaryImg);

		return salonUpdate;
	}

	@Override
	public Salon initModel() throws Exception {
		Salon salon = (Salon) coreHelper.createModelStructure(new Salon());

		return salon;
	}

	@Override
	public List<Salon> getAllSalon() throws Exception {
		Iterable<Salon> salons = salonRepo.findAll();
		List<Salon> salonList = new ArrayList<Salon>();
		salons.forEach(salon -> {
			salonList.add(salon);
		});
		return salonList;
	}

	@Override
	public void delete(Salon salon) {
		salonRepo.delete(salon);

	}

	@Override
	public Salon extendLicense(UUID salonId, UUID licenseId, int type, int month) throws Exception {
		// TODO Auto-generated method stub
		Date now = new Date();
		Salon salon = salonRepo.findOne(salonId);
		if (salon == null) {
			throw new Exception("Can't find salon with id");
		}
		License license = licenseRepo.findOne(licenseId);

		SalonLicense salonLicense = salon.getSalonLicense();

		if (DateUtil.compare(now, salonLicense.getExpiredDate()) == -1) {
			salonLicense.setExpiredDate(DateUtil.addMonth(salonLicense.getExpiredDate(), month));
		} else {
			salonLicense.setExpiredDate(DateUtil.addMonth(now, month));
		}

		salonLicense = salonLicenseRepository.save(salonLicense);
		int smsRemain = salonLicense.getSmsRemain() + license.getNumFreeSms();
		int emailRemain = salonLicense.getEmailRemain() + license.getNumFreeEmail();
		salonLicense.setSmsRemain(smsRemain);
		salonLicense.setEmailRemain(emailRemain);
		salonLicense.setLicense(license);
		salon = salonRepo.save(salon);

		return salon;
	}

}
