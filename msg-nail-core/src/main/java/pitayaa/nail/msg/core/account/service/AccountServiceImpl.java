package pitayaa.nail.msg.core.account.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.account.Account;
import pitayaa.nail.domain.category.Category;
import pitayaa.nail.domain.license.License;
import pitayaa.nail.domain.salon.Salon;
import pitayaa.nail.domain.service.ServiceModel;
import pitayaa.nail.domain.setting.SettingSms;
import pitayaa.nail.json.account.JsonAccount;
import pitayaa.nail.json.account.JsonAccountLogin;
import pitayaa.nail.msg.business.account.AccountBus;
import pitayaa.nail.msg.business.category.CategoryBus;
import pitayaa.nail.msg.business.service.ServiceBus;
import pitayaa.nail.msg.business.setting.SettingSMSBus;
import pitayaa.nail.msg.core.account.repository.AccountLicenseRepository;
import pitayaa.nail.msg.core.account.repository.AccountRepository;
import pitayaa.nail.msg.core.category.repository.CategoryRepository;
import pitayaa.nail.msg.core.common.HttpHelper;
import pitayaa.nail.msg.core.license.service.ILicenseService;
import pitayaa.nail.msg.core.salon.service.SalonService;
import pitayaa.nail.msg.core.serviceEntity.repository.ServiceRepository;
import pitayaa.nail.msg.core.setting.sms.repository.SettingSmsRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountLicenseRepository accountLicenseRepository;
	
	@Autowired
	SettingSmsRepository settingSMSRepo;

	@Autowired
	CategoryRepository categoryRepo;
	
	@Autowired
	ServiceRepository serviceRepo;
	
	
	@Autowired
	ILicenseService licenseService;

	@Autowired
	AccountBus accountBusiness;
	
	@Autowired
	SettingSMSBus settingSMSBus;
	
	@Autowired
	CategoryBus categoryBus;
	
	@Autowired
	ServiceBus serviceBus;
	
	@Autowired
	SalonService salonService;

	@Autowired
	HttpHelper httpHelper;

	@Override
	public Account saveAccount(Account accountBody) {
		accountBody = accountRepository.save(accountBody);

		return accountBody;
	}

	@Override
	public JsonAccount registerAccount(Account accountBody) throws Exception {

		// Get License Trial For Account
		License licenseTrial = licenseService.getLicenseTrial();

		// Execute business process
		Account isExistUsername = accountRepository.findAccountByUsername(accountBody.getUsername()); 
		accountBody = accountBusiness
				.registerAccount(accountBody, licenseTrial , isExistUsername);

		//Save salon
		Salon salon = accountBody.getSalon().get(0);
		salon= salonService.save(salon);
		
		//save setting sms
		List<SettingSms> lstSettingSMS=settingSMSBus.getListSettingSMSDefault(salon.getUuid().toString());
		for(SettingSms settingSms:lstSettingSMS){
			settingSMSRepo.save(settingSms);
		}
		
		//save default category
		
		List<Category> lstCategory=categoryBus.getListServiceCategoryDefault(salon.getUuid().toString());
		for(Category category:lstCategory){
			category=categoryRepo.save(category);
		}
		
		//save default service
		
		List<ServiceModel> lstService=serviceBus.getListServiceDefault(salon.getUuid().toString());
		Category category=lstCategory.get(0);
		for(ServiceModel serviceModel:lstService){
			serviceModel.setCategoryId(category.getUuid().toString());
			serviceRepo.save(serviceModel);
		}
		
		
		
		
		// Save
		accountBody = accountRepository.save(accountBody);
		
		// Parse to json
		JsonAccount jsonAccount = accountBusiness.parseAccountToJson(accountBody);
		
		return jsonAccount;

	}

	@Override
	public Account findAccount(UUID id) {

		return accountRepository.findOne(id);
	}

	@Override
	public JsonAccount loginProcess(JsonAccountLogin jsonAccountLogin) throws Exception {

		//JsonHttp result = new JsonHttp();

		// Map jsonAccountLogin to AccountModel
		String username = jsonAccountLogin.getUsername();
		String password = jsonAccountLogin.getPassword();

		// Query
		Account findAccount = accountRepository
				.loginProcess(username, password);

		// Execute business & update
		findAccount = accountBusiness.loginProcess(findAccount);
		findAccount = accountRepository.save(findAccount);

		// Parse to json
		JsonAccount jsonAccount= accountBusiness.parseAccountToJson(findAccount);

		return jsonAccount;
	}

}
