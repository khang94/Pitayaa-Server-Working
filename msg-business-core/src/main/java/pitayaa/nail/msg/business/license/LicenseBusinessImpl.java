package pitayaa.nail.msg.business.license;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.license.License;
import pitayaa.nail.msg.business.constant.LicenseConstant;
import pitayaa.nail.msg.business.helper.BusinessHelper;


@Service
public class LicenseBusinessImpl implements LicenseBusiness {
	
	@Autowired
	BusinessHelper businessHelper;
	
	@Override
	public License saveLicense(License licenseBody){
		
		Date timeNow = businessHelper.getTimeNow();
		
		licenseBody.setCreatedDate(timeNow);
		licenseBody.setUpdatedDate(timeNow);
		
		licenseBody.setStatus(1);
		
		return licenseBody;
	}
	
	@Override
	public License updateLicense(License licenseBody){
		
		Date timeNow = businessHelper.getTimeNow();
		licenseBody.setUpdatedDate(timeNow);	
		return licenseBody;
		
	}




}
