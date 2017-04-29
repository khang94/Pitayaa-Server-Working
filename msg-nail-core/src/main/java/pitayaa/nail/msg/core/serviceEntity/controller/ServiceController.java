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
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.serviceEntity.service.ServiceEntityInterface;

@Controller
public class ServiceController {

	@Autowired
	private CoreHelper coreHelper;
	
	@Autowired
	private ServiceEntityInterface serviceCore;

	@RequestMapping(value = "services/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initAccountModel() throws Exception {

		ServiceModel account = (ServiceModel) coreHelper
				.createModelStructure(new ServiceModel());

		return ResponseEntity.ok(account);
	}
	
	@RequestMapping(value = "services/bySalon", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getServicesBySalonId(@RequestParam("salonId") String salonId) throws Exception {

		List<ServiceModel> servicesList = serviceCore.getServicesBySalonId(salonId);
		JsonHttp jsonHttp = new JsonHttp();
		if (servicesList!= null && servicesList.size() > 0){
			jsonHttp.setCode(200);
			jsonHttp.setObject(servicesList);
			jsonHttp.setStatus("success");
			jsonHttp.setResponseMessage("get list success");
		} 
		
		else{
			jsonHttp.setCode(404);
			jsonHttp.setStatus("error");
			jsonHttp.setResponseMessage("get list failed");
		} 
		return ResponseEntity.ok(jsonHttp);
	}
	
	@RequestMapping(value = "services", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> savePackage(@RequestBody ServiceModel serviceModel) throws Exception {

		serviceCore.save(serviceModel);
		return ResponseEntity.ok(serviceModel);
	}
	
	@RequestMapping(value = "services/{ID}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateService(@PathVariable("ID") UUID uid ,@RequestBody ServiceModel serviceUpdated) throws Exception {

		Optional<ServiceModel> serviceSaved = serviceCore.findOne(uid);
		
		if(!serviceSaved.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		ServiceModel service = serviceCore.update(serviceSaved.get(), serviceUpdated);
		return ResponseEntity.ok(service);
	}
}
