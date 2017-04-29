package pitayaa.nail.msg.core.account.controller;

import java.util.Optional;
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

import pitayaa.nail.domain.account.elements.AccountLicense;
import pitayaa.nail.msg.core.account.service.AccountLicenseService;
import pitayaa.nail.msg.core.common.CoreHelper;

@Controller
public class AccountLicenseController {

	@Autowired
	private CoreHelper coreHelper;

	@Autowired
	private AccountLicenseService accLicenseService;

	@RequestMapping(value = "/accountsLicense/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initAccountLicenseModel()
			throws Exception {

		AccountLicense jsonAccountLogin = (AccountLicense) coreHelper
				.createModelStructure(new AccountLicense());

		return ResponseEntity.ok(jsonAccountLogin);
	}
	
	@RequestMapping(value = "/accountsLicense/{ID}", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> saveAccountLicenseModel(
			@RequestBody AccountLicense accLicenseBody,
			@PathVariable("ID") UUID uid) throws Exception {

		Optional<AccountLicense> accountLicense = accLicenseService
				.findOne(uid);

		if (!accountLicense.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		accLicenseBody = accLicenseService.save(accLicenseBody);

		return ResponseEntity.ok(accLicenseBody);
	}

	@RequestMapping(value = "/accountsLicense/{ID}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateAccountLicenseModel(
			@RequestBody AccountLicense accLicenseBody,
			@PathVariable("ID") UUID uid) throws Exception {

		Optional<AccountLicense> accountLicense = accLicenseService
				.findOne(uid);

		if (!accountLicense.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		accLicenseBody = accLicenseService.update(accLicenseBody);

		return ResponseEntity.ok(accLicenseBody);
	}

	@RequestMapping(value = "/accountsLicense/{ID}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findOneAccLicense(
			@PathVariable("ID") UUID uid) throws Exception {

		Optional<AccountLicense> accountLicense = accLicenseService
				.findOne(uid);

		if (!accountLicense.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(accountLicense, HttpStatus.OK);
		}
	}

}
