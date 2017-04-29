package pitayaa.nail.domain.notification.hibernate.transaction;

import java.util.Date;

import org.springframework.stereotype.Service;

import pitayaa.nail.domain.notification.common.Metadata;
import pitayaa.nail.domain.notification.email.EmailModel;
import pitayaa.nail.domain.notification.scheduler.SmsQueue;
import pitayaa.nail.domain.notification.sms.SmsModel;

@Service
public class ObjectListenerHelper {

	// Set create & update dated every time call post request
	protected Object setDateInsert(Object ob) {
		if (ob.getClass().equals(EmailModel.class)){
			EmailModel email = (EmailModel) ob;
			if(email.getMeta() == null){
				email.setMeta(new Metadata());
			}
			email.getMeta().setCreatedDate(new Date());
			email.getMeta().setUpdatedDate(new Date());
		} else if (ob.getClass().equals(SmsModel.class)){
			SmsModel sms = (SmsModel) ob;
			if(sms.getMeta() == null){
				sms.setMeta(new Metadata());
			}
			sms.getMeta().setCreatedDate(new Date());
			sms.getMeta().setUpdatedDate(new Date());
			
			if(sms.getSmsReceive() != null){
				sms.getSmsReceive().setCreatedDate(new Date());
				sms.getSmsReceive().setUpdatedDate(new Date());
			}
		} else if (ob.getClass().equals(SmsQueue.class)){
			SmsQueue queue = (SmsQueue) ob;
			queue.setCreatedDate(new Date());
			queue.setUpdatedDate(new Date());
		}
		return ob;
	}

	// Set update dated every time call put request
	public Object setDateUpdate(Object ob) {
		if (ob.getClass().equals(EmailModel.class)){
			EmailModel email = (EmailModel) ob;
			if(email.getMeta() == null){
				email.setMeta(new Metadata());
			}
			email.getMeta().setUpdatedDate(new Date());
		} else if (ob.getClass().equals(SmsModel.class)){
			SmsModel sms = (SmsModel) ob;
			if(sms.getMeta() == null){
				sms.setMeta(new Metadata());
			}
			sms.getMeta().setUpdatedDate(new Date());
			if(sms.getSmsReceive() != null){
				sms.getSmsReceive().setCreatedDate(new Date());
			}
		} else if (ob.getClass().equals(SmsQueue.class)){
			SmsQueue queue = (SmsQueue) ob;
			queue.setUpdatedDate(new Date());
		}
		return ob;
	}

}
