package pitayaa.nail.msg.core.systemconf.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pitayaa.nail.domain.systemconf.SystemConf;
import pitayaa.nail.domain.view.View;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.business.json.JsonHttpService;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.systemconf.service.SystemConfService;

@Controller
public class SystemConfController {

	@Autowired
	private CoreHelper coreHelper;

	@Autowired
	private SystemConfService systemConfService;
	
	@Autowired
	private JsonHttpService httpService;

	
	@RequestMapping(value = "systemconf/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initAccountModel() throws Exception {

		SystemConf systemConf = (SystemConf) coreHelper
				.createModelStructure(new SystemConf());

		return ResponseEntity.ok(systemConf);
	}

	@RequestMapping(value = "systemconf/{Id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateSetting(
			@PathVariable("Id") UUID uid, @RequestBody SystemConf systemConf)
			throws Exception {
		
		JsonHttp jsonHttp = new JsonHttp();
		Optional<SystemConf> systemConfSave = systemConfService.findOne(uid);
		
		if(!systemConfSave.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		try {
			systemConf = systemConfService.save(systemConf);
			jsonHttp = httpService.getResponseSuccess(systemConf, "Update system config successfully...");

		} catch (Exception e) {
			jsonHttp = httpService.getResponseError("ERROR", e.getMessage());
		}
		return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
	}

	@RequestMapping(value = "systemconf/bySalonAndType", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getPackagesBySalonId(
			@RequestParam("salonId") String salonId,
			@RequestParam("key") String key, @RequestParam("type") String type)
			throws Exception {

		Optional<SystemConf> conf = systemConfService.findModelBy(salonId, type,
				key);
		if(!conf.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		JsonHttp jsonHttp = new JsonHttp();
		
		try {
			jsonHttp = httpService.getResponseSuccess(conf.get(), "get data successfully");
		} catch (Exception ex){
			jsonHttp = httpService.getResponseError("ERROR", ex.getMessage());
		}
		
		return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());

	}

	@RequestMapping(value = "systemconf/allSetting", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getAllSetting(
			@RequestParam("salonId") String salonId) throws Exception {
		HashMap<String, Object> data = new HashMap<>();

		Optional<SystemConf> conf = systemConfService.findModelBy(salonId,
				"BACKGROUND", "PERSONALIZE");
		data.put("background", conf.get());
		JsonHttp jsonHttp = new JsonHttp();

		jsonHttp.setCode(200);
		jsonHttp.setObject(data);
		jsonHttp.setStatus("success");
		return ResponseEntity.ok(jsonHttp);

	}
	
	@RequestMapping(value = "systemconf", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findAllSetting(@RequestParam("salonId") String salonId) throws Exception {
		
		JsonHttp jsonHttp = new JsonHttp();
		
		try {
			jsonHttp = httpService.getResponseSuccess(systemConfService.getAllConfigPreferences(salonId), "Get all data successfully..");
		} catch (Exception ex){
			jsonHttp = httpService.getResponseError("ERROR", "Get all data failed...");
		}
		return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
	}
	
	@RequestMapping(value = "systemconf", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> updateSetting(@RequestBody List<SystemConf> systemConfs ,@RequestParam("salonId") String salonId) throws Exception {
		
		JsonHttp jsonHttp = new JsonHttp();
		
		try {
			jsonHttp = httpService.getResponseSuccess(systemConfService.updateSystemConfiguration(salonId, systemConfs), "Get all data successfully..");
		} catch (Exception ex){
			jsonHttp = httpService.getResponseError("ERROR", "Get all data failed...");
		}
		return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
	}
	
	@RequestMapping(value = "systemconf/{Id}/upload", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> uploadImage(@PathVariable("Id") UUID id , 
			@RequestBody View viewBody ,@RequestParam("salonId") String salonId) throws Exception {
		
		Optional<SystemConf> systemData = systemConfService.findOne(id);
		if(!systemData.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		JsonHttp jsonHttp = new JsonHttp();
		
		try {
			jsonHttp = httpService.getResponseSuccess(
					systemConfService.uploadImageToSetting(viewBody, salonId, systemData.get()), "Get all data successfully..");
		} catch (Exception ex){
			jsonHttp = httpService.getResponseError("ERROR", "Get all data failed...");
		}
		return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
	}
	
	
	
	
	


}
