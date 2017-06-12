package pitayaa.nail.notification.scheduler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pitayaa.nail.domain.notification.scheduler.SmsQueue;
import pitayaa.nail.domain.setting.SettingSms;
import pitayaa.nail.json.http.JsonHttp;
import pitayaa.nail.notification.common.NotificationHelper;
import pitayaa.nail.notification.scheduler.service.SmsQueueService;
import pitayaa.nail.notification.sms.config.SmsConstant;
import pitayaa.nail.notification.sms.service.ISmsService;

@Controller
public class SmsQueueController {
	
	@Autowired
	SmsQueueService queueService;
	
	@Autowired
	NotificationHelper notificationHelper;
	
	
	@RequestMapping(value = "smsQueue/model" , method = RequestMethod.GET)
	public ResponseEntity<?> getQueueModel() throws Exception{
		SmsQueue smsQueue = (SmsQueue) notificationHelper.createModelStructure(new SmsQueue());
		
		return new ResponseEntity<> (smsQueue , HttpStatus.OK);
	}
	
	@RequestMapping(value = "smsQueue" , method = RequestMethod.POST)
	public ResponseEntity<?> createSmsQueue(@RequestBody SmsQueue smsQueue){
		smsQueue = queueService.saveQueue(smsQueue);
		
		return new ResponseEntity<> (smsQueue , HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "smsQueue" ,method = RequestMethod.GET)
	public ResponseEntity<?> getQueueSms (@RequestParam("customerId") String customerId ,
			@RequestParam("settingSmsId") String settingSmsId, @RequestParam("customerType") String customerType){
		List<SmsQueue> smsQueue = queueService.getQueue(customerId, settingSmsId, customerType);
		
		return new ResponseEntity<>(smsQueue , HttpStatus.OK);
	}
	
	@RequestMapping(value = "smsQueue/action" , method = RequestMethod.POST)
	public ResponseEntity<?> deliverMessage(@RequestBody SettingSms settingSms , @RequestParam("operation") String operation) throws Exception{
		
		JsonHttp jsonHttp = new JsonHttp();
		
		if(operation.equals(SmsConstant.OPERATION_DELIVER)){
			jsonHttp = queueService.deliverMessage(settingSms);
		}

		
		return new ResponseEntity<>(jsonHttp , HttpStatus.OK);
	}

}
