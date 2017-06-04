package pitayaa.nail.msg.core.systemconf.controller;

import java.util.HashMap;
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

import pitayaa.nail.domain.setting.SettingSms;
import pitayaa.nail.domain.systemconf.SystemConf;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.core.common.CoreHelper;
import pitayaa.nail.msg.core.systemconf.service.SystemConfService;

@Controller
public class SystemConfController {

	@Autowired
	private CoreHelper coreHelper;

	@Autowired
	private SystemConfService systemConfService;

	private JsonHttp jsonHttp;

	@RequestMapping(value = "systemconf/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> initAccountModel() throws Exception {

		SystemConf systemConf = (SystemConf) coreHelper
				.createModelStructure(new SystemConf());

		return ResponseEntity.ok(systemConf);
	}

	@RequestMapping(value = "systemConf/{Id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateSetting(
			@PathVariable("Id") UUID uid, @RequestBody SystemConf systemConf)
			throws Exception {
		jsonHttp = new JsonHttp();

		try {
			Optional<SystemConf> systemConfSave = systemConfService
					.findOne(uid);
			if (!systemConfSave.isPresent()) {
				throw new Exception("Can't find this object");
			}
			systemConf = systemConfService.save(systemConf);
			jsonHttp.setCode(200);
			jsonHttp.setStatus("success");
			jsonHttp.setObject(systemConf);

		} catch (Exception e) {
			jsonHttp.setCode(201);
			jsonHttp.setStatus("error");
			jsonHttp.setMessage(e.getMessage());
		}

		return ResponseEntity.ok(jsonHttp);
	}

	@RequestMapping(value = "systemconf/bySalonAndType", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getPackagesBySalonId(
			@RequestParam("salonId") String salonId,
			@RequestParam("key") String key, @RequestParam("type") String type)
			throws Exception {

		Optional<SystemConf> conf = systemConfService.findModelBy(salonId, type,
				key);
		JsonHttp jsonHttp = new JsonHttp();

		if (conf.isPresent()) {
			jsonHttp.setCode(200);
			jsonHttp.setObject(conf.get());
			jsonHttp.setStatus("success");
			return ResponseEntity.ok(jsonHttp);
		} else {
			jsonHttp.setCode(404);
			jsonHttp.setStatus("error");
			return ResponseEntity.ok(jsonHttp);
		}

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
	


}
