package pitayaa.nail.msg.business.account;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.account.elements.AccountLicense;
import pitayaa.nail.domain.license.License;
import pitayaa.nail.msg.business.helper.BusinessHelper;
import pitayaa.nail.msg.business.salon.SalonBus;

@Service
public class AccountLicenseBusImpl implements AccountLicenseBus {

	@Autowired
	BusinessHelper businessHelper;

	@Autowired
	SalonBus salonBusiness;

	@Override
	public AccountLicense save(AccountLicense accLicense) {
		Date timeNow = businessHelper.getTimeNow();

		accLicense.setActivedDate(timeNow);
		accLicense.setUpdatedDate(timeNow);
		accLicense.setCreatedDate(timeNow);

		return accLicense;
	}

	@Override
	public AccountLicense update(AccountLicense accLicense) {
		Date timeNow = businessHelper.getTimeNow();

		accLicense.setUpdatedDate(timeNow);

		return accLicense;
	}

	@Override
	public AccountLicense createDefaultAccountLicense(
			AccountLicense accountLicense, License license) {

		// Create system information
		//Date timeNow = businessHelper.getTimeNow();
		//accountLicense.setActivedDate(timeNow);
		//accountLicense.setCreatedDate(timeNow);
		//accountLicense.setUpdatedDate(timeNow);

		// Set license to account license
		accountLicense.setLicense(license);

		return accountLicense;
	}

}
