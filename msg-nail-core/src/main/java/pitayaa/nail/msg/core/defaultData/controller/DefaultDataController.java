package pitayaa.nail.msg.core.defaultData.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.msg.business.json.JsonHttpService;
import pitayaa.nail.msg.core.defaultData.service.DefaultDataService;

@Controller
public class DefaultDataController {

	@Autowired
	private DefaultDataService defaultDataService;

	@Autowired
	private JsonHttpService httpService;

	private JsonHttp data;

	@RequestMapping(value = "default/create", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> createDefaultDataForApp() throws Exception {

		try {
			defaultDataService.defaultDataForApp();
			data = httpService.getResponseSuccess(null, "create default data success");

		} catch (Exception e) {
			data = httpService.getResponseError(e.getMessage(), e.getMessage());

		}

		return new ResponseEntity<>(data, data.getHttpCode());

	}

}
