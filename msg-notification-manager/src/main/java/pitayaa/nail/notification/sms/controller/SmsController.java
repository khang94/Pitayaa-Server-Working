package pitayaa.nail.notification.sms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pitayaa.nail.domain.notification.sms.SmsModel;
import pitayaa.nail.notification.email.config.EmailConstant;
import pitayaa.nail.notification.sms.service.InteractionService;
import pitayaa.nail.notification.sms.service.SmsService;


@Controller
public class SmsController {

	@Autowired
	SmsService smsService;
	
	@Autowired
	SmsControllerImpl controllerImpl;
	
	@Autowired
	InteractionService interactionService;

	@RequestMapping(value = "sms/about", method = RequestMethod.GET)
	public @ResponseBody HttpEntity<?> about() {
		String message = "Welcome to Sms Microservice group !";
		return ResponseEntity.ok(message);
	}

	@RequestMapping(value = "sms/model", method = RequestMethod.GET)
	public @ResponseBody HttpEntity<?> getModel() throws Exception {
		SmsModel sms = smsService.initModelSms();
		Resource<SmsModel> resource = new Resource<SmsModel>(sms);

		return new ResponseEntity<>(sms, HttpStatus.OK);
	}

	@RequestMapping(value = "sms", method = RequestMethod.POST)
	public @ResponseBody HttpEntity<?> createSms(
			@RequestBody SmsModel smsBody,
			@RequestParam(value = "operation", required = false, defaultValue = "") String operation)
			throws Exception {

		SmsModel sms = null;
		if (EmailConstant.OPERATION_DELIVER.equalsIgnoreCase(operation)) {
			sms = smsService.sendSms(smsBody);
		} else {
			sms = smsService.createSms(smsBody);
		}
		return new ResponseEntity<>(sms, HttpStatus.OK);
	}

	@RequestMapping(value = "sms/reply", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> replySms(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		
		SmsModel sms = controllerImpl.handleRequestFromCustomer(req, resp);

		return new ResponseEntity<>(sms, HttpStatus.OK);
	}

	@RequestMapping(value = "sms/delivery", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> deliverySms(@RequestParam("key") String key) throws Exception {
		SmsModel smsModel = interactionService.findSmsByResponseKeyTest(key);
		return new ResponseEntity<>(smsModel , HttpStatus.OK);
	}

	@RequestMapping(value = "sms/delivery", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getSms() {
		System.out.println("sms-->delivery");
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
