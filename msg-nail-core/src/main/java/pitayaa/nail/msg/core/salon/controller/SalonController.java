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

import pitayaa.nail.domain.account.Account;
import pitayaa.nail.domain.salon.Salon;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.business.json.JsonHttpService;
import pitayaa.nail.msg.core.salon.service.SalonService;

@Controller
public class SalonController {

	@Autowired
	private SalonService salonService;

	@Autowired
	private JsonHttpService httpService;

	private JsonHttp data;

	@RequestMapping(value = "salons/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initAccountModel() throws Exception {

		Salon salon = salonService.initModel();
		return ResponseEntity.ok(salon);
	}

	@RequestMapping(value = "salons/{Id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findOne(@PathVariable("Id") UUID uid)
			throws Exception {

		Optional<Salon> savedSalon = salonService.findOne(uid);

		if (!savedSalon.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(savedSalon.get());
	}

	@RequestMapping(value = "salons/{Id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> findOne(@PathVariable("Id") UUID uid,
			@RequestBody Salon salonUpdate) throws Exception {

		Optional<Salon> savedSalon = salonService.findOne(uid);

		if (!savedSalon.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Salon salon = salonService.save(salonUpdate);
		return ResponseEntity.ok(salon);
	}

	@RequestMapping(value = "salons", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> findOne(@RequestBody Salon salon)
			throws Exception {

		salon = salonService.save(salon);
		return new ResponseEntity<>(salon, HttpStatus.CREATED);
	}

	@RequestMapping(value = "salons", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getListSalon() throws Exception {
		List<Salon> salons = new ArrayList<Salon>();
		salons = salonService.getAllSalon();
		return new ResponseEntity<>(salons, HttpStatus.OK);
	}

	@RequestMapping(value = "salons/extend", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> extendLicense(
			@PathVariable("ID") UUID uid, @RequestBody Salon salon)
			throws Exception {
		Optional<Salon> oldSalon = salonService.findOne(uid);
		if (oldSalon.isPresent()) {
			salon = salonService.update(salon, oldSalon.get());
			data = httpService.getResponseSuccess(salon, "extend success");
		} else {
			data = httpService.getResponseError("cannot find salon", null);
		}

		return ResponseEntity.ok(data);
	}

	@RequestMapping(value = "salons/{ID}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("ID") UUID id)
			throws Exception {

		Optional<Salon> salon = salonService.findOne(id);
		JsonHttp json = new JsonHttp();

		if (!salon.isPresent()) {
			json = httpService.getResponseError("Not found this salon", "");
		}

		try {
			salonService.delete(salon.get());
			json = httpService.getResponseSuccess("ok", "success");

		} catch (Exception ex) {
			json = httpService.getResponseError("Delete failed....",
					ex.getMessage());
		}
		return ResponseEntity.ok(json);

	}
}
