package pitayaa.nail.msg.business.license;

import pitayaa.nail.domain.license.License;



public interface LicenseBusiness {

	License saveLicense(License licenseBody);

	License updateLicense(License licenseBody);




}
