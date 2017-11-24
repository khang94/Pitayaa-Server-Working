package pitayaa.nail.msg.core.account.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pitayaa.nail.domain.account.Account;
import pitayaa.nail.json.account.JsonAccount;
import pitayaa.nail.json.account.JsonAccountLogin;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.business.json.JsonHttpService;
import pitayaa.nail.msg.business.util.security.EncryptionUtils;
import pitayaa.nail.msg.core.account.repository.AccountRepository;
import pitayaa.nail.msg.core.account.service.AccountService;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.hibernate.QueryCriteria;

@Controller
public class AccountController {

	@Autowired
	private CoreHelper coreHelper;

	@Autowired
	private AccountService accountService;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	JsonHttpService httpService;

	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> about() {
		String message = "Welcome to core service Pitayaa Nail Group !";

		return ResponseEntity.ok(message);
	}

	@RequestMapping(value = "/accounts/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initAccountModel() throws Exception {

		Account jsonAccountLogin = (Account) coreHelper.createModelStructure(new Account());

		return ResponseEntity.ok(jsonAccountLogin);
	}

	@RequestMapping(value = "/accounts/search", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> getAllAccount(@RequestBody(required = false) QueryCriteria query,Pageable pageable) throws Exception {

		JsonHttp json = new JsonHttp();
		try {
			List<Account> listAccount = accountService.findAllByQuery(query.getQuery(), pageable);
			json = httpService.getResponseSuccess(listAccount, "Get all account success");
		} catch (Exception e) {
			json = httpService.getResponseError("ERROR", e.getMessage());
		}
		return ResponseEntity.ok(json);
	}

	@RequestMapping(value = "/accounts/{ID}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getAccount(@PathVariable("ID") UUID id) throws Exception {

		Account account = accountService.findAccount(id);

		return ResponseEntity.ok(account);
	}

	@RequestMapping(value = "/accounts/register", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> registerAccount(@RequestBody Account accountBody) throws Exception {

		JsonHttp jsonHttp = new JsonHttp();
		try {
			JsonAccount account = accountService.registerAccount(accountBody);
			jsonHttp = httpService.getResponseSuccess(account, "Register this account successfully");
		} catch (Exception e) {
			jsonHttp = httpService.getResponseError("Register failed", e.getMessage());
		}
		return new ResponseEntity<>(jsonHttp, jsonHttp.getHttpCode());
	}

	@RequestMapping(value = "/accounts/login", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> login(@RequestBody JsonAccountLogin jsonAccountLogin) throws Exception {

		JsonHttp jsonHttp = new JsonHttp();
		try {
			JsonAccount accountLogin = accountService.loginProcess(jsonAccountLogin);

			if (accountLogin == null) {
				throw new Exception("Can't find account with email!");
			}
			jsonHttp = httpService.getResponseSuccess(accountLogin, "Login success");

		} catch (Exception e) {
			jsonHttp = httpService.getResponseError("Login failed", e.getMessage());
		}

		return new ResponseEntity<>(jsonHttp, jsonHttp.getHttpCode());

	}

	@RequestMapping(value = "/accounts", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> saveAccount(@RequestBody Account account) throws Exception {

		account = accountService.saveAccount(account);

		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}

	@RequestMapping(value = "/accounts/login/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getLoginModel() throws Exception {

		JsonAccountLogin jsonAccountLogin = (JsonAccountLogin) coreHelper.createModelStructure(new JsonAccountLogin());

		return new ResponseEntity<JsonAccountLogin>(jsonAccountLogin, HttpStatus.OK);
	}

	@RequestMapping(value = "accounts/{Id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> update(@PathVariable("Id") UUID uid ,@RequestBody Account account) throws Exception {
		JsonHttp json = new JsonHttp();
		try {
			Account oldAccount = accountService.findAccount(uid);
			if(oldAccount==null){
				throw new Exception("Can't find this account");
			}
			
			//check if update password
			if(account.getPassword()!=null){
				String encryptPass=EncryptionUtils.encodeMD5(account.getPassword(), account.getContact().getEmail());
				account.setPassword(encryptPass);
			}else{
				account.setPassword(oldAccount.getPassword());
			}
			
			account = accountService.saveAccount(account);
			json = httpService.getResponseSuccess(account, "update account success");
		} catch (Exception e) {
			json = httpService.getResponseError("ERROR", e.getMessage());
		}
		return ResponseEntity.ok(json);
	
	}
	
	@RequestMapping(value = "accounts/{ID}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("ID") UUID id) throws Exception {

		Account account = accountService.findAccount(id);
		JsonHttp json = new JsonHttp();
		
		if(account==null){
			json = httpService.getResponseError("Not found this account","");
		}

		try {
			accountService.delete(account);
			json = httpService.getResponseSuccess("ok", "success");
			
		} catch (Exception ex) {
			json = httpService.getResponseError("Delete failed...." , ex.getMessage());
		}
		return ResponseEntity.ok(json);

	}
}
