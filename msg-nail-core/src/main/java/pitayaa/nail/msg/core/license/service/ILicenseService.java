package pitayaa.nail.msg.core.license.service;

import java.util.Optional;
import java.util.UUID;

import pitayaa.nail.domain.license.License;

public interface ILicenseService {

	License getLicenseTrial();

	License saveLicense(License licenseBody);

	License updateLicense(License licenseBody);

	boolean deleteLicense(UUID uid);

	Optional<License> findOne(UUID uid);

}
