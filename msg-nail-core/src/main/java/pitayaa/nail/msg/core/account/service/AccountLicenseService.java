package pitayaa.nail.msg.core.account.service;

import java.util.Optional;
import java.util.UUID;

import pitayaa.nail.domain.account.elements.AccountLicense;


public interface AccountLicenseService {

	AccountLicense save(AccountLicense accountLicense);

	Optional<AccountLicense> findOne(UUID id);

	AccountLicense update(AccountLicense accountLicense);

}
