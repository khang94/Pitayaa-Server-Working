package pitayaa.nail.notification.promotion.business;

import java.text.ParseException;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.notification.scheduler.SmsQueue;
import pitayaa.nail.domain.setting.SettingSms;
import pitayaa.nail.domain.setting.sms.CustomerSummary;
import pitayaa.nail.notification.common.NotificationConstant;
import pitayaa.nail.notification.scheduler.JobHelper;
import pitayaa.nail.notification.scheduler.QuartJob;
import pitayaa.nail.notification.scheduler.TimeUtils;

@Service
public class PromotionJobBusImpl implements PromotionJobBus {

	//@Autowired
	//SmsQueueService queueService;
	
	private JobHelper initJobHelper(){
		
		ApplicationContext ctx = QuartJob.applicationContext;
		JobHelper jobHelper = ctx.getBean(JobHelper.class);
		
		return jobHelper;
	}
	

	@Override
	public boolean isTimeToSend(SettingSms settingSms, CustomerSummary customerSummary) {

		// Get last time checkin
		Date lastActionSms = customerSummary.getCustomerDetail().getLastCheckin();

		// Is right time to send sms
		boolean isSend = TimeUtils.isRightTimeToSend(settingSms, lastActionSms , NotificationConstant.TIME_REPEAT);

		if (isSend) {
			return true;
		}

		// Check right time repeat to send
		if (settingSms.isEnableRepeat()) {
			
			JobHelper jobHelper = this.initJobHelper();
			SmsQueue queue = jobHelper.openQueue(customerSummary.getCustomerRefID(), settingSms); 

			if (queue != null) {
				lastActionSms = queue.getSendTime();
				isSend = TimeUtils.isRightTimeToSend(settingSms, lastActionSms , NotificationConstant.SCHEDULER_TIME_REPEAT);
			}
		}

		return isSend;
	}
	
	@Override
	public boolean isTimeToSendGroups(SettingSms settingSms) throws ParseException{
		
		// Get time notify
		String getTimeString = settingSms.getSendSmsOn() + " " + settingSms.getSendSmsOnTime();
		Date timeNotify = TimeUtils.getDateFromString(getTimeString);
		
		boolean isSend = TimeUtils.isRightTimeToSend(settingSms, timeNotify , NotificationConstant.TIME_REPEAT);
		
		return isSend;
	}
	
	@Override
	public boolean checkTimeProcess(SettingSms settingSms ,CustomerSummary customerSummary) throws ParseException{
		
		boolean isTimeToSend = false;
		if(NotificationConstant.CUSTOMER_PROMOTION.equalsIgnoreCase(settingSms.getKey())){
			isTimeToSend = this.isTimeToSendGroups(settingSms);
		} else if (NotificationConstant.CUSTOMER_APPOINTMENT_REMIND.equalsIgnoreCase(settingSms.getKey())){
			
		} else {
			isTimeToSend = this.isTimeToSend(settingSms , customerSummary);
		}
		
		return isTimeToSend;
	}

}
