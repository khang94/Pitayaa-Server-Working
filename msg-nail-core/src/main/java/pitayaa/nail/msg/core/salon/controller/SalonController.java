package pitayaa.nail.msg.core.salon.controller;

import java.util.ArrayList;
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

import pitayaa.nail.domain.salon.Salon;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.business.json.JsonHttpService;
import pitayaa.nail.msg.core.salon.service.SalonService;

@Controller
public class SalonController {
	
	@Autowired
	private SalonService salonService;
	
	@Autowired
	private JsonHttpService jsonService;
	
	private JsonHttp data;
	
	@RequestMapping(value = "salons/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initAccountModel() throws Exception {
		
		Salon salon = salonService.initModel();
		return ResponseEntity.ok(salon);
	}
	
	@RequestMapping(value = "salons/{Id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findOne(@PathVariable("Id") UUID uid) throws Exception {

		Optional<Salon> savedSalon = salonService.findOne(uid);
		
		if(!savedSalon.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(savedSalon.get());
	}
	
	@RequestMapping(value = "salons/{Id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> findOne(@PathVariable("Id") UUID uid ,@RequestBody Salon salonUpdate) throws Exception {

		Optional<Salon> savedSalon = salonService.findOne(uid);
		
		if(!savedSalon.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Salon salon = salonService.save(salonUpdate);
		return ResponseEntity.ok(salon);
	}
	
	@RequestMapping(value = "salons", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> findOne(@RequestBody Salon salon) throws Exception {

		salon = salonService.save(salon);
		return new ResponseEntity<>(salon , HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "salons", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getListSalon() throws Exception{
		List<Salon> salons = new ArrayList<Salon>();
		salons = salonService.getAllSalon();
		return new ResponseEntity<>(salons , HttpStatus.OK);
	}
	
	@RequestMapping(value = "salons/extend", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> extendLicense(@PathVariable("ID") UUID uid, @RequestBody Salon salon) throws Exception {
		Optional<Salon> oldSalon=salonService.findOne(uid);
		if(oldSalon.isPresent()){
			salon=salonService.update(salon, oldSalon.get());
			data=jsonService.getResponseSuccess(salon, "extend success");
		}else{
			data=jsonService.getResponseError("cannot find salon", null);
		}

		return ResponseEntity.ok(data);
	}
}
