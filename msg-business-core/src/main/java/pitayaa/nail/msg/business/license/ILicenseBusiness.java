package pitayaa.nail.msg.business.license;

import pitayaa.nail.domain.license.License;



public interface ILicenseBusiness {

	License saveLicense(License licenseBody);

	License updateLicense(License licenseBody);




}
