package pitayaa.nail.msg.business.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.account.Account;
import pitayaa.nail.domain.account.elements.AccountLicense;
import pitayaa.nail.domain.license.License;
import pitayaa.nail.domain.salon.Salon;
import pitayaa.nail.json.account.JsonAccount;
import pitayaa.nail.msg.business.helper.BusinessHelper;
import pitayaa.nail.msg.business.helper.GsonCustom;
import pitayaa.nail.msg.business.salon.SalonBus;

import com.google.gson.Gson;

@Service
public class AccountBusImpl implements AccountBus {

	@Autowired
	BusinessHelper businessHelper;

	@Autowired
	SalonBus salonBus;

	@Autowired
	AccountLicenseBus accLicenseBus;

	@Override
	public Account registerAccount(Account account, License license , Account isExistUsername) throws Exception {
		
		// Validate account with username
		boolean isExisted = this.validateRegister(isExistUsername);
		if(isExisted){
			throw new Exception("Account with this username have already been existed ! Cannot register with this username !");
		}

		// Create Account License
		AccountLicense accountLicense = (AccountLicense) businessHelper.createModelStructure(new AccountLicense());
		accountLicense = accLicenseBus
				.createDefaultAccountLicense(accountLicense,
						license);

		// Create Salon for account
		Salon salon = salonBus.createDefaultSalon(account, license);
		List<Salon> salonGroup = new ArrayList<Salon>();
		salonGroup.add(salon);

		// Add salon to account
		account.setSalon(salonGroup);
		account.setAccountLicense(accountLicense);

		return account;
	}
	
	@Override
	public boolean validateRegister(Account isExistUsername) throws Exception {
		return (isExistUsername != null) ? true : false;
	}
	
	@Override
	public Account loginProcess (Account account) throws Exception{
		if (account != null){
			// Set last login
			Date timeNow = businessHelper.getTimeNow();
			account.getAccountDetail().setLastLogin(timeNow);
		} else {
			throw new Exception("This account does not exist");
		}
		
		return account;
	}
	
	
	
	@Override
	public JsonAccount parseAccountToJson (Account account) throws Exception{
		
		GsonCustom gsonCustom = businessHelper.getJsonFromObject(account);
		
		// Extract gson custom
		Gson gson = gsonCustom.getGsonObject();
		String jsonInString = gsonCustom.getJsonString();
		
		// Parse to json Account
		JsonAccount jsonAccount = gson.fromJson(jsonInString, JsonAccount.class);
		
		return jsonAccount;
	}

}
