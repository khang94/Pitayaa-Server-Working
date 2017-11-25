package pitayaa.nail.msg.core.license.controller;

import java.util.List;
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

import pitayaa.nail.domain.license.License;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.business.json.JsonHttpService;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.license.service.LicenseService;

@Controller
public class LicenseController {

	@Autowired
	private CoreHelper coreHelper;

	@Autowired
	private LicenseService licenseService;
	
	@Autowired
	private JsonHttpService httpService;

	@RequestMapping(value = "licenses/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initAccountModel() throws Exception {

		License account = (License) coreHelper
				.createModelStructure(new License());

		return ResponseEntity.ok(account);
	}
	
	
	@RequestMapping(value = "licenses", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getAll() throws Exception {
		JsonHttp jsonHttp = new JsonHttp();	
		
		try {
			List <License>licenses = licenseService.findAll();
			jsonHttp = httpService.getResponseSuccess(licenses, "Get license success");
		}catch (Exception ex){
			jsonHttp = httpService.getResponseError("Get list failed...." , ex.getMessage());
		}

		return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
	}


	@RequestMapping(value = "licenses", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> createLicense(@RequestBody License licenseBody) throws Exception {

		License license = licenseService.saveLicense(licenseBody);

		return new ResponseEntity<>(license , HttpStatus.CREATED);
	}

	@RequestMapping(value = "licenses/{ID}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateLicense(@PathVariable("ID") UUID uid, @RequestBody License licenseBody) throws Exception {

		License license = licenseService.updateLicense(licenseBody);

		return ResponseEntity.ok(license);
	}

	@RequestMapping(value = "licenses/{ID}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> deleteLicense(@PathVariable("ID") UUID uid) throws Exception {

		// Find license
		Optional<License> optionalLicense = licenseService.findOne(uid);
		if(!optionalLicense.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		// Delete license
		boolean isDelete = licenseService.deleteLicense(uid);
		
		// Get status
		HttpStatus status = (isDelete) ? HttpStatus.NO_CONTENT : HttpStatus.EXPECTATION_FAILED;

		// Return
		return new ResponseEntity<>(status);
	}
}
