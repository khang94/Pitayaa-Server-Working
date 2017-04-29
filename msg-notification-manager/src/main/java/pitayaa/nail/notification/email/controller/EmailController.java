package pitayaa.nail.notification.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pitayaa.nail.domain.notification.email.EmailModel;
import pitayaa.nail.notification.common.NotificationHelper;
import pitayaa.nail.notification.email.service.IEmailService;

@Controller
public class EmailController {

	@Autowired
	IEmailService emailService;
	
	@Autowired
	NotificationHelper notificationHelper;

	@RequestMapping(value = "email/about", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> about() {
		String message = "Welcome to Email Microservice group !";
		return ResponseEntity.ok(message);
	}
	
	@RequestMapping(value = "email/model", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getEmailModel() throws Exception {
		EmailModel email = (EmailModel) notificationHelper.createModelStructure(new EmailModel());
		return ResponseEntity.ok(email);
	}



}
