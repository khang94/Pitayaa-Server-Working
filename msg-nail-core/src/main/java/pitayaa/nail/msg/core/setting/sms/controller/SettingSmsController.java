package pitayaa.nail.msg.core.setting.sms.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pitayaa.nail.domain.setting.SettingSms;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.business.json.JsonHttpService;
import pitayaa.nail.msg.core.common.CoreConstant;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.setting.sms.service.SettingSmsService;

@Controller
public class SettingSmsController {

	@Autowired
	private CoreHelper coreHelper;

	@Autowired
	private SettingSmsService settingSmsService;
	
	@Autowired
	private JsonHttpService httpService;

	@RequestMapping(value = "settingSms/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initSettingSms() throws Exception {

		SettingSms settingSms = (SettingSms) coreHelper.createModelStructure(new SettingSms());

		return ResponseEntity.ok(settingSms);
	}
	
	@RequestMapping(value = "settingSms", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> createSetting(@RequestBody SettingSms settingSms) throws Exception {

		settingSms = settingSmsService.save(settingSms);

		return ResponseEntity.ok(settingSms);
	}
	
	@RequestMapping(value = "settingSms/{Id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateSetting(@PathVariable("Id") UUID uid,
			@RequestBody SettingSms settingSms) throws Exception {

		Optional<SettingSms> savedSettingSms = settingSmsService.findOne(uid);
		
		if(!savedSettingSms.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		settingSms = settingSmsService.save(settingSms);

		return ResponseEntity.ok(settingSms);
	}
	
	@RequestMapping(value = "settingSms/{Id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> createSetting(@PathVariable("Id") UUID uid) throws Exception {

		Optional<SettingSms> savedSettingSms = settingSmsService.findOne(uid);
		
		if(!savedSettingSms.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(savedSettingSms.get() , HttpStatus.OK);
	}
	
	@RequestMapping(value = "settingSms/salons", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getListSetting(@Param("salonId") String salonId , 
			@RequestParam(name = "operation" , required = false , defaultValue = "") String operation) throws Exception {
		
		Optional<List<SettingSms>> lstSetting = settingSmsService.getListSettingSMS(salonId);
		JsonHttp jsonHttp = new JsonHttp();
		
		if(CoreConstant.OPERATION_REFRESH.equalsIgnoreCase(operation)){
			return ResponseEntity.ok(lstSetting.get());
		}
		
		if(!lstSetting.isPresent()){
			jsonHttp = httpService.getNotFoundData("Not found list setting for this salon [" + salonId + "]");
		}
		jsonHttp = httpService.getResponseSuccess(lstSetting.get(), "Get list setting success..");

		return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
	}
	
	@RequestMapping(value = "settingSms/salons", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateListSetting(@Param("salonId") String salonId,
			@RequestBody List<SettingSms> lstSettingSms,
			@RequestParam(name = "operation" , required = false , defaultValue = "") String operation) throws Exception {
		
		Optional<List<SettingSms>> lstSetting = settingSmsService.getListSettingSMS(salonId);
		JsonHttp jsonHttp = new JsonHttp();
		
		if(!lstSetting.isPresent() || lstSetting.get().size() == 0){
			jsonHttp = httpService.getNotFoundData("Not found list setting for salon [" + salonId + "]  to updated.");
		}
		
		try {
			lstSettingSms = settingSmsService.updateListSettingSms(salonId, lstSettingSms);
			jsonHttp = httpService.getResponseSuccess(lstSettingSms, "Update list setting sms for salon Id [" + salonId + "] successfully !");
		} catch (Exception ex){
			jsonHttp = httpService.getResponseError("Updated list setting for salon failed", ex.getMessage());
		}
		
		
		if(CoreConstant.OPERATION_REFRESH.equalsIgnoreCase("operation")){
			return ResponseEntity.ok(lstSettingSms);
		}
		

		return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
	}
	
}
