package pitayaa.nail.msg.business.account;

import pitayaa.nail.domain.account.elements.AccountLicense;
import pitayaa.nail.domain.license.License;






public interface AccountLicenseBus {

	AccountLicense save(AccountLicense accLicense);

	AccountLicense update(AccountLicense accLicense);

	AccountLicense createDefaultAccountLicense(AccountLicense accountLicense,
			License license);




}
