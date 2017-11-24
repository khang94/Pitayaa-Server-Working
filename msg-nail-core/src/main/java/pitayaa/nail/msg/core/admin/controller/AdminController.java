package pitayaa.nail.msg.core.admin.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pitayaa.nail.domain.account.Account;
import pitayaa.nail.domain.admin.Admin;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.business.json.JsonHttpService;
import pitayaa.nail.msg.business.util.security.EncryptionUtils;
import pitayaa.nail.msg.core.admin.service.AdminService;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	JsonHttpService httpService;

	@RequestMapping(value = "/admins/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initAccountModel() throws Exception {

		Admin json = adminService.initModel();
		return ResponseEntity.ok(json);
	}

	@RequestMapping(value = "/admins", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getAllAdmin(Pageable pageable)
			throws Exception {

		JsonHttp json = new JsonHttp();
		json.setCode(200);
		try {
			Page<Admin> admins = adminService.findAll(pageable);
			json = httpService.getResponseSuccess(admins,
					"Get all data for admin successfully...");
		} catch (Exception e) {
			json = httpService.getResponseError("ERROR",
					"There is exception while loading data");
		}
		return ResponseEntity.ok(json);
	}

	@RequestMapping(value = "/admins/{ID}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getAdmin(@PathVariable("ID") UUID id)
			throws Exception {

		Admin admin = adminService.findAdmin(id);

		return ResponseEntity.ok(admin);
	}

	@RequestMapping(value = "/admins/register", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> registerAccount(
			@RequestBody Admin admin) throws Exception {

		JsonHttp jsonHttp = new JsonHttp();
		try {
			admin = adminService.register(admin);
			jsonHttp = httpService.getResponseSuccess(admin,
					"Register this account successfully");
		} catch (Exception e) {
			jsonHttp = httpService.getResponseError("Register failed",
					e.getMessage());
		}
		return new ResponseEntity<>(jsonHttp, jsonHttp.getHttpCode());
	}

	@RequestMapping(value = "/admins/login", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> loginAdmin(@RequestBody Admin admin)
			throws Exception {

		JsonHttp jsonHttp = new JsonHttp();
		try {
			admin = adminService.login(admin);
			if (admin != null) {
				jsonHttp = httpService.getResponseSuccess(admin,
						"login this account successfully");

			} else {
				jsonHttp = httpService.getResponseError("Login failed",
						"Incorrect username or password");

			}
		} catch (Exception e) {
			jsonHttp = httpService.getResponseError("Login failed",
					e.getMessage());
		}
		return new ResponseEntity<>(jsonHttp, jsonHttp.getHttpCode());
	}

	@RequestMapping(value = "admins/{Id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> update(@PathVariable("Id") UUID uid,
			@RequestBody Admin admin) throws Exception {
		JsonHttp json = new JsonHttp();
		try {
			Admin oldAdmin = adminService.findAdmin(uid);
			if (oldAdmin == null) {
				throw new Exception("Can't find this admin");
			}
			
			//check if update password
			if(admin.getPassword()!=null){
				String encryptPass=EncryptionUtils.encodeMD5(admin.getPassword(), admin.getContact().getEmail());
				admin.setPassword(encryptPass);
			}else{
				admin.setPassword(oldAdmin.getPassword());
			}
			
			admin = adminService.save(admin);
			json = httpService.getResponseSuccess(admin,
					"update admin success");
		} catch (Exception e) {
			json = httpService.getResponseError("ERROR", e.getMessage());
		}
		return ResponseEntity.ok(json);

	}

	@RequestMapping(value = "admins/{ID}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("ID") UUID id)
			throws Exception {

		Admin admin = adminService.findAdmin(id);
		JsonHttp json = new JsonHttp();

		if (admin == null) {
			json = httpService.getResponseError("Not found this admin", "");
		}

		try {
			adminService.delete(admin);;
			json = httpService.getResponseSuccess("ok", "success");

		} catch (Exception ex) {
			json = httpService.getResponseError("Delete failed....",
					ex.getMessage());
		}
		return ResponseEntity.ok(json);

	}
}
