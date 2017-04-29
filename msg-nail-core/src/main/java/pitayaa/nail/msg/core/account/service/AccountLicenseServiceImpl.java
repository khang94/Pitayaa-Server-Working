package pitayaa.nail.msg.core.account.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.account.elements.AccountLicense;
import pitayaa.nail.msg.business.account.AccountLicenseBus;
import pitayaa.nail.msg.core.account.repository.AccountLicenseRepository;

@Service
public class AccountLicenseServiceImpl implements AccountLicenseService{
	
	@Autowired
	AccountLicenseRepository accountLicenseRepo;
	
	@Autowired
	AccountLicenseBus accountLicenceBus;
	
	@Override
	public Optional<AccountLicense> findOne(UUID id){
		return Optional.ofNullable(accountLicenseRepo.findOne(id));
	}

	@Override
	public AccountLicense save(AccountLicense accountLicense){
		
		// Execute Business
		AccountLicense accLicense = accountLicenceBus.save(accountLicense);
		
		// Save to repo
		accLicense = accountLicenseRepo.save(accLicense);
		
		return accLicense;
	}
	
	@Override
	public AccountLicense update(AccountLicense accountLicense){
		
		// Execute Business
		AccountLicense accLicense = accountLicenceBus.update(accountLicense);
		
		// Save to repo
		accLicense = accountLicenseRepo.save(accLicense);
		
		return accLicense;
	}
}
