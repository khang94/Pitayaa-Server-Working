package pitayaa.nail.msg.core.defaultData.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.license.License;
import pitayaa.nail.domain.license.elements.LicenseDetail;
import pitayaa.nail.domain.license.elements.Price;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.license.repository.LicenseRepository;
import pitayaa.nail.msg.core.salon.repository.SalonRepository;
import pitayaa.nail.msg.core.salon.service.SalonService;
import pitayaa.nail.msg.core.setting.service.SettingService;

@Service
public class DefaultDataServiceImpl implements DefaultDataService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DefaultDataServiceImpl.class);

	@Autowired
	SalonRepository salonRepository;

	@Autowired
	LicenseRepository licenseRepository;

	@Autowired
	CoreHelper coreHelper;

	@Autowired
	SettingService settingService;

	@Autowired
	SalonService salonService;

	@Override
	public void defaultDataForApp() {
		// TODO Auto-generated method stub
		// default license
		License license = new License();
		license.setLicenseName("Trial");
		license.setNumDevices(2);
		license.setNumClientProfiles(2);;
		license.setNumFreeEmail(500);;
		license.setNumFreeSms(100);
		license.setStatus(1);
		license.setTrial(true);

		LicenseDetail licenseDetail = new LicenseDetail();
		Price licPrice = new Price();
		licPrice.setPrice(0.0);
		licenseDetail.setLicensePrice(licPrice);
		license.setLicenseDetail(licenseDetail);
		licenseRepository.save(license);
		// standard

		license = new License();
		license.setLicenseName("Standard");
		license.setNumDevices(5);
		license.setNumClientProfiles(5);;
		license.setNumFreeEmail(1500);;
		license.setNumFreeSms(1100);
		license.setTrial(false);

		licenseDetail = new LicenseDetail();
		licPrice = new Price();
		licPrice.setPrice(500.0);
		licenseDetail.setLicensePrice(licPrice);
		license.setLicenseDetail(licenseDetail);

		licenseRepository.save(license);
		// professional
		license = new License();
		license.setLicenseName("Professional");
		license.setNumDevices(5);
		license.setNumClientProfiles(5);;
		license.setNumFreeEmail(1500);;
		license.setNumFreeSms(1100);
		license.setTrial(false);

		licenseDetail = new LicenseDetail();
		licPrice = new Price();
		licPrice.setPrice(600.0);
		licenseDetail.setLicensePrice(licPrice);
		license.setLicenseDetail(licenseDetail);
		licenseRepository.save(license);
		
		// ultimate
		license = new License();
		license.setLicenseName("Ultimate");
		license.setNumDevices(5);
		license.setNumClientProfiles(5);;
		license.setNumFreeEmail(1500);;
		license.setNumFreeSms(1100);
		license.setTrial(false);

		licenseDetail = new LicenseDetail();
		licPrice = new Price();
		licPrice.setPrice(700.0);
		licenseDetail.setLicensePrice(licPrice);
		license.setLicenseDetail(licenseDetail);

		licenseRepository.save(license);

		// ============================== End license
		// ==============================//

	}

}
