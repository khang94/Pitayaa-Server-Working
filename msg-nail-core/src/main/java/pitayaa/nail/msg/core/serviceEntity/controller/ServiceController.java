package pitayaa.nail.msg.core.serviceEntity.controller;

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

import pitayaa.nail.domain.service.ServiceModel;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.business.json.JsonHttpService;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.serviceEntity.service.ServiceEntityInterface;

@Controller
public class ServiceController {

	@Autowired
	private CoreHelper coreHelper;
	
	@Autowired
	private ServiceEntityInterface serviceCore;
	
	@Autowired
	private JsonHttpService httpService;

	@RequestMapping(value = "services/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initAccountModel() throws Exception {

		ServiceModel service = serviceCore.initModel();

		return ResponseEntity.ok(service);
	}
	
	@RequestMapping(value = "services/salons", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getServicesBySalonId(@RequestParam("salonId") String salonId) throws Exception {

		JsonHttp jsonHttp = new JsonHttp();	
		
		try {
			List<ServiceModel> servicesList = serviceCore.getServicesBySalonId(salonId);
			jsonHttp = httpService.getResponseSuccess(servicesList, "Get list service success");
		}catch (Exception ex){
			jsonHttp = httpService.getResponseError("Get list failed...." , ex.getMessage());
		}

		return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
	}
	
	@RequestMapping(value = "services", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> savePackage(@RequestBody ServiceModel serviceBody) throws Exception {

		JsonHttp jsonHttp = new JsonHttp();
		
		try {
			serviceBody = serviceCore.save(serviceBody);
			jsonHttp = httpService.saveDataSuccess(serviceBody , "Saved service successfully....");
		} catch (Exception ex){
			jsonHttp = httpService.getResponseError("Saved service failed...." , ex.getMessage());
		}
		return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
	}
	
	@RequestMapping(value = "services/{ID}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> update(@RequestBody ServiceModel serviceUpdated,
			@PathVariable("ID") UUID id) throws Exception {
		
		Optional<ServiceModel> serviceSaved = serviceCore.findOne(id);
		JsonHttp jsonHttp = new JsonHttp();
		
		if(!serviceSaved.isPresent()){
			jsonHttp = httpService.getNotFoundData("Not found this services");
			return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
		}
		
		
		try {
			ServiceModel serviceResult = serviceCore.update(serviceSaved.get() , serviceUpdated);	
			jsonHttp = httpService.getResponseSuccess(serviceResult, "Update service successfully !");
		} catch (Exception ex ) {
			jsonHttp = httpService.getResponseError("Update this service failed" , ex.getMessage());
		}
		
		return new ResponseEntity<>(jsonHttp, jsonHttp.getHttpCode());

	}
	
	@RequestMapping(value = "services/{ID}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findById(@PathVariable("ID") UUID id) throws Exception {
		
		Optional<ServiceModel> serviceSaved = serviceCore.findOne(id);
		JsonHttp jsonHttp = new JsonHttp();
		
		if(!serviceSaved.isPresent()){
			jsonHttp = httpService.getNotFoundData("Not found this services");
			return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
		}
		
		jsonHttp = httpService.getResponseSuccess(serviceSaved.get(), "Get service successfully !");
		
		return new ResponseEntity<>(jsonHttp, jsonHttp.getHttpCode());

	}
	
	@RequestMapping(value = "services/{ID}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("ID") UUID id) throws Exception {

		Optional<ServiceModel> serviceSaved = serviceCore.findOne(id);
		JsonHttp jsonHttp = new JsonHttp();
		
		if(!serviceSaved.isPresent()){
			jsonHttp = httpService.getNotFoundData("Not found this services");
			return new ResponseEntity<>(jsonHttp , jsonHttp.getHttpCode());
		}

		try {
			serviceCore.delete(serviceSaved.get());
			jsonHttp = httpService.deleteData("Delete success.....");
			
		} catch (Exception ex) {
			jsonHttp = httpService.getResponseError("Delete failed...." , ex.getMessage());
		}
		return new ResponseEntity<>(jsonHttp, jsonHttp.getHttpCode());

	}
}
