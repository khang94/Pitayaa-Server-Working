package pitayaa.nail.msg.core.license.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.license.License;
import pitayaa.nail.msg.business.license.ILicenseBusiness;
import pitayaa.nail.msg.core.license.repository.LicenseRepository;

@Service
public class LicenseServiceImpl implements ILicenseService {

	@Autowired
	private LicenseRepository licenseRepo;

	@Autowired
	private ILicenseBusiness licenseBusiness;

	@Override
	public License getLicenseTrial() {
		return licenseRepo.getLicenseTrial();
	}
	
	@Override
	public Optional<License> findOne(UUID uid) {
		return Optional.ofNullable(licenseRepo.findOne(uid));
	}

	@Override
	public License saveLicense(License licenseBody) {

		licenseBody = licenseBusiness.saveLicense(licenseBody);
		return licenseRepo.save(licenseBody);
	}

	@Override
	public License updateLicense(License licenseBody) {

		licenseBody = licenseBusiness.updateLicense(licenseBody);
		return licenseRepo.save(licenseBody);
	}

	@Override
	public boolean deleteLicense(UUID uid) {
		boolean check = false;
		licenseRepo.delete(uid);
		
		// Find again
		if(licenseRepo.findOne(uid) == null){
			check = true;
		}
		
		return check;
	}
}
