package pitayaa.nail.notification.sms.controller;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.notification.sms.SmsModel;
import pitayaa.nail.domain.notification.sms.SmsReceive;
import pitayaa.nail.msg.business.util.common.StringUtil;
import pitayaa.nail.notification.sms.config.SmsConstant;
import pitayaa.nail.notification.sms.service.ISmsService;

@Service
public class SmsControllerImpl {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(SmsControllerImpl.class);
	
	@Autowired
	ISmsService smsService;
	
	public SmsModel handleRequestFromCustomer(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String strTemp = null;
			SmsReceive receiveBody = new SmsReceive();
			
			strTemp = request.getParameter(SmsConstant.PHONE_SENDER);
			if(!strTemp.equalsIgnoreCase("") && strTemp != null){
				receiveBody.setFromPhone(strTemp);
			}
			
			strTemp = request.getParameter(SmsConstant.PHONE_TO);
			if(!strTemp.equalsIgnoreCase("") && strTemp != null){
				receiveBody.setToPhone(strTemp);
			}
			
			strTemp = request.getParameter(SmsConstant.MESSAGE_ID);
			if(!strTemp.equalsIgnoreCase("") && strTemp != null){
				receiveBody.setMessageId(strTemp);
			}
			
			strTemp = request.getParameter(SmsConstant.CONTENT_MESSAGE);
			if(!strTemp.equalsIgnoreCase("") && strTemp != null){
				receiveBody.setMessage(strTemp);
			}
			
			strTemp = request.getParameter(SmsConstant.TYPE_MESSAGE);
			if(!strTemp.equalsIgnoreCase("") && strTemp != null){
				receiveBody.setSmsType(strTemp);
			}
			
			strTemp = request.getParameter(SmsConstant.TIMESTAMP_MESSAGE);
			if(!strTemp.equalsIgnoreCase("") && strTemp != null){
				receiveBody.setCreatedDate(format.parse(strTemp));
			}
			
			// log info
			StringBuilder info = new StringBuilder();
			info.append("  Customer phone number: ").append(receiveBody.getFromPhone()).append("\t");
			info.append(". Salon phone number: ").append(receiveBody.getToPhone()).append("\t");
			info.append(". Sms content: ").append(receiveBody.getMessage());
			
			LOGGER.info(info.toString());
			
			// handle
			if(!receiveBody.getMessageId().equalsIgnoreCase("")
					&& !receiveBody.getToPhone().equalsIgnoreCase("")
					&& !receiveBody.getFromPhone().equalsIgnoreCase("")){
				//smsMgr.handleSmsReceiveInfo(receiveBody);
				smsService.saveSmsReceive(receiveBody);
			} else{
				LOGGER.info("Empty message id or empty sms to or sms from a.");
			}
		} catch (Exception e) {
			LOGGER.info("error when received sms message. Exception: ", e.getMessage());
		}
		return null;
	}

}
