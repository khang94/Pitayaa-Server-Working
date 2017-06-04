package pitayaa.nail.msg.core.account.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pitayaa.nail.domain.account.Account;
import pitayaa.nail.json.account.JsonAccount;
import pitayaa.nail.json.account.JsonAccountLogin;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.business.util.security.EncryptionUtils;
import pitayaa.nail.msg.core.account.repository.AccountRepository;
import pitayaa.nail.msg.core.account.service.AccountService;
import pitayaa.nail.msg.core.common.CoreHelper;

@Controller
public class AccountController {

	@Autowired
	private CoreHelper coreHelper;

	@Autowired
	private AccountService accountService;
	
	@Autowired
	AccountRepository accountRepository;

	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> about() {
		String message = "Welcome to core service Pitayaa Nail Group !";

		return ResponseEntity.ok(message);
	}

	@RequestMapping(value = "/accounts/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initAccountModel() throws Exception {

		Account jsonAccountLogin = (Account) coreHelper
				.createModelStructure(new Account());

		return ResponseEntity.ok(jsonAccountLogin);
	}
	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getAllAccount() throws Exception {

		List<Account> listAccount = new ArrayList<Account>();
		
		listAccount = (List<Account>) accountRepository.findAll();
			
		return ResponseEntity.ok(listAccount);
	}

	@RequestMapping(value = "/accounts/{ID}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getAccount(
			@PathVariable("ID") UUID id) throws Exception {

		Account account = accountService.findAccount(id);

		return ResponseEntity.ok(account);
	}

	@RequestMapping(value = "/accounts/register", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> registerAccount(
			@RequestBody Account accountBody) throws Exception {

		JsonHttp json=new JsonHttp();
		json.setCode(200);
		try{
			JsonAccount account = accountService.registerAccount(accountBody);
			json.setStatus("success");
			json.setObject(account);
		}catch(Exception e){
			json.setCode(500);
			json.setStatus("error");
			json.setMessage(e.getMessage());
		}
		return ResponseEntity.ok(json);
	}

	@RequestMapping(value = "/accounts/login", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> login(
			@RequestBody JsonAccountLogin jsonAccountLogin) throws Exception {

		JsonHttp json=new JsonHttp();
		try{
			String encryptionPassword=EncryptionUtils.encodeMD5(jsonAccountLogin.getPassword(), jsonAccountLogin.getEmail());
			jsonAccountLogin.setPassword(encryptionPassword);
			JsonAccount accountLogin = accountService
					.loginProcess(jsonAccountLogin);
			
			if (accountLogin == null) {
				throw new Exception ("Can't find account with email!");
			}
			
			json.setStatus(JsonHttp.SUCCESS);
			json.setObject(accountLogin);

		}catch(Exception e){
			json.setMessage(e.getMessage());
			json.setStatus(JsonHttp.ERROR);
		}
	
		return ResponseEntity.ok(json);

	}

	@RequestMapping(value = "/accounts", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> saveAccount(
			@RequestBody Account account) throws Exception {

		account = accountService.saveAccount(account);

		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}

	@RequestMapping(value = "/accounts/login/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getLoginModel() throws Exception {

		JsonAccountLogin jsonAccountLogin = (JsonAccountLogin) coreHelper
				.createModelStructure(new JsonAccountLogin());

		return new ResponseEntity<JsonAccountLogin>(jsonAccountLogin,
				HttpStatus.OK);
	}

}
