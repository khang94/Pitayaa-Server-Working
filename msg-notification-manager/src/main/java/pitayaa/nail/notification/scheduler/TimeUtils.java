package pitayaa.nail.notification.scheduler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pitayaa.nail.domain.customer.Customer;
import pitayaa.nail.domain.setting.SettingSms;
import pitayaa.nail.domain.setting.sms.CustomerSummary;

public class TimeUtils {
	
	public final static Logger LOGGER = LoggerFactory.getLogger(TimeUtils.class);

	public static boolean compareDate(Date currentDate, Date lastModify, int timeNotify) {

		boolean isCorrectTime = false;

		long dateNotify = System.currentTimeMillis();
		long dateModify = lastModify.getTime();

		long gapNotify = 86400000 * timeNotify;

		long timeExpectNotify = dateModify + gapNotify;

		if (dateNotify > timeExpectNotify && (dateNotify - timeExpectNotify) <= 86400000) {
			isCorrectTime = true;
		} else if (timeExpectNotify >= dateNotify) {
			isCorrectTime = true;
		}
		return isCorrectTime;
	}

	
	public static boolean isTimeNotify(Date lastSms , SettingSms settingSms){
		boolean isSend = false;
		
		long lastSmsSend = lastSms.getTime();
		long currentTime = System.currentTimeMillis();
		
		long timeExpectNotify = 0;
		if (settingSms.getTimesRepeat() != 0 && settingSms.getMinutesRepeat() ==0){
			timeExpectNotify = lastSmsSend + 86400000 * settingSms.getTimesRepeat();
		} else if (settingSms.getMinutesRepeat() != 0 && settingSms.getTimesRepeat() == 0){
			timeExpectNotify = lastSmsSend + 60000 * settingSms.getMinutesRepeat();
		}
		
		if(timeExpectNotify > currentTime && (timeExpectNotify - currentTime) <= 150000){
			isSend = true;
		} else if (timeExpectNotify == currentTime){
			isSend = true;
		} else if (currentTime > timeExpectNotify && (currentTime - timeExpectNotify) <= 150000){
			isSend = true;
		}
		return isSend;
	}
	
	public static boolean checkLastTimeSms(Date lastSms , SettingSms settingSms){
		boolean isRightTime = false;
		
		long lastSmsSend = lastSms.getTime();
		long currentTime = System.currentTimeMillis();
		
		long gapNotify = 0;
		
		// DAY
		if(settingSms.getTimesRepeat() > 0 && settingSms.getMinutesRepeat() == 0 && settingSms.getHoursRepeat() == 0){
			gapNotify = 86400000 * settingSms.getTimesRepeat();
		} 
		// MINUTES
		else if (settingSms.getTimesRepeat() == 0 && settingSms.getMinutesRepeat() > 0 && settingSms.getHoursRepeat() == 0){
			gapNotify = 60000 * settingSms.getMinutesRepeat();
		} 
		// HOURS
		else if (settingSms.getTimesRepeat() == 0 && settingSms.getMinutesRepeat() == 0 && settingSms.getHoursRepeat() > 0){
			gapNotify = 60 * 60000 * settingSms.getHoursRepeat();
		}
		
		long timeExpectToNotify = lastSmsSend + gapNotify;
		
		if (currentTime > timeExpectToNotify && (currentTime - timeExpectToNotify) < 60000){
			isRightTime = true;
		} else if (currentTime == timeExpectToNotify){
			isRightTime = true;
		} else if (currentTime < timeExpectToNotify && (timeExpectToNotify - currentTime) < 60000){
			isRightTime = true;
		}
		

		return isRightTime;
	}
	
	
	
	public static Date getDateFromString (String time) throws ParseException{
		Date date = null;
		try {
			DateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH : mm");
			date=formatter.parse(time);
		} catch (Exception ex){
			LOGGER.info("Time input [" + time + "] is invalid !");
		}
		return date;
	}
	
	public static boolean compareTime (Date currentDate , Date notifyDate){
		
		long currentTime = currentDate.getTime();
		long notifyTime = notifyDate.getTime();
		
		boolean isCorrectTime = false;
		
		if (currentTime > notifyTime && (currentTime - notifyTime) <= 150000){
			isCorrectTime = true;
		} else if (currentTime == notifyTime){
			isCorrectTime = true;
		} else if (currentTime < notifyTime && (notifyTime - currentTime) <= 150000){
			isCorrectTime = true;
		}
		
		return isCorrectTime;
	}
	
	public static void main(String[] args) throws ParseException{
		String date = "2017-04-06 ";
		String time = "22 : 25";
		
		String fullTime = date + time;
		
		Date result = getDateFromString(fullTime);
		System.out.println(result);
		
	}
	
	public static boolean isFirstTimeNotify(Customer customer , SettingSms settingSms){
		
		 boolean isCorrectTime = false;
	
		// Get current time
		long currentTime = System.currentTimeMillis();
		
		// Get first time when customer visit
		long firstVisitCustomer = customer.getCreatedDate().getTime();
		
		// time expect to notify after long time
		long gapNotify = 0;
		if (settingSms.getTimesRepeat() != 0 && settingSms.getMinutesRepeat() ==0){
			gapNotify = 86400000 * settingSms.getTimesRepeat();
		} else if (settingSms.getMinutesRepeat() != 0 && settingSms.getTimesRepeat() == 0){
			gapNotify = 60000 * settingSms.getMinutesRepeat();
		}
		long timeExpectToNotify = firstVisitCustomer + gapNotify;
		
		if((timeExpectToNotify - firstVisitCustomer) > gapNotify){
			return true;
		}
		
		if(currentTime > timeExpectToNotify && (currentTime - timeExpectToNotify) < 150000){
			isCorrectTime = true;
		} else if(currentTime == timeExpectToNotify){
			isCorrectTime = true;
		} else if (currentTime < timeExpectToNotify && (timeExpectToNotify - currentTime) < 150000){
			isCorrectTime = true;
		}
		return isCorrectTime;
	}
	
	public static boolean isRightTimeToSend(SettingSms settingSms , CustomerSummary customerSummary){
		
		// Get last checkin time
		long lastCheckin = customerSummary.getCustomerDetail().getLastCheckin().getTime();
		
		// Get current time
		long currentTime = System.currentTimeMillis();
		
		long gapNotify = 0;
		
		boolean isRightTime = false;
		
		// Get time type
		
		// DAY
		if(settingSms.getTimesRepeat() > 0 && settingSms.getMinutesRepeat() == 0 && settingSms.getHoursRepeat() == 0){
			gapNotify = 86400000 * settingSms.getTimesRepeat();
		} 
		// MINUTES
		else if (settingSms.getTimesRepeat() == 0 && settingSms.getMinutesRepeat() > 0 && settingSms.getHoursRepeat() == 0){
			gapNotify = 60000 * 1;
		} 
		// HOURS
		else if (settingSms.getTimesRepeat() == 0 && settingSms.getMinutesRepeat() == 0 && settingSms.getHoursRepeat() > 0){
			gapNotify = 60 * 60000 * settingSms.getHoursRepeat();
		}
		
		long timeExpectToNotify = lastCheckin + gapNotify;
		
		if (currentTime > timeExpectToNotify && (currentTime - timeExpectToNotify) < 90000){
			isRightTime = true;
		} else if (currentTime == timeExpectToNotify){
			isRightTime = true;
		} else if (currentTime < timeExpectToNotify && (timeExpectToNotify - currentTime) < 90000){
			isRightTime = true;
		}
		
		return isRightTime;
	}

}
