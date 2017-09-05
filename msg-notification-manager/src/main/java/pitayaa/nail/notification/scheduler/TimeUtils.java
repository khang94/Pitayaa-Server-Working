package pitayaa.nail.notification.scheduler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pitayaa.nail.domain.setting.SettingSms;
import pitayaa.nail.notification.common.NotificationConstant;

public class TimeUtils {
	
	public final static Logger LOGGER = LoggerFactory.getLogger(TimeUtils.class);
	
	
	public static String FORMAT_TIME = "MM-dd-yyyy hh-mm-ss";
	
	public static String START_TIME = "00-00-00";
	public static String END_TIME = "23-59-59";
	
	public static String SPACE = " ";
	
	public static Date getStartDate(String date) throws Exception{
		
		DateFormat df = new SimpleDateFormat(FORMAT_TIME); 
		
		String time = date + SPACE + START_TIME;
		Date startDate = new Date();
		try {
		    startDate = df.parse(time);
		    LOGGER.info("Start date [{}]" , startDate);
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		return startDate;
	} 
	
	public static Date getEndDate(String date) throws Exception{
		
		DateFormat df = new SimpleDateFormat(FORMAT_TIME); 
		
		String time = date + SPACE + END_TIME;
		Date endDate = new Date();
		try {
			endDate = df.parse(time);
			LOGGER.info("End date [{}]" , endDate);
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		return endDate;
	} 
	
	public static Date getCurrentUTCTime(){
		
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		Calendar calendar = Calendar.getInstance(timeZone);
		SimpleDateFormat simpleDateFormat = 
		       new SimpleDateFormat("EE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
		simpleDateFormat.setTimeZone(timeZone);

		LOGGER.info("Time zone: " + timeZone.getID());
		LOGGER.info("default time zone: " + TimeZone.getDefault().getID());
		System.out.println();

		LOGGER.info("UTC:     " + simpleDateFormat.format(calendar.getTime()));
		LOGGER.info("Default: " + calendar.getTime());
		
		return calendar.getTime();
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
			gapNotify = 60000 * 3;
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
		Date currentTime = getCurrentUTCTime();
		
		LOGGER.info("Current Time [" + currentTime + "]");
		
	}
	
	public static long getGapNotify (SettingSms settingSms){
		
		long gapNotify = 0;
		
		// Get time type
		
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
		
		return gapNotify;
	}
	
	public static boolean isTimeSend(long timeExpectToNotify){
		
		// Get current time
		long currentTime = System.currentTimeMillis();
		
		boolean isRightTime = false;
		
		if (currentTime > timeExpectToNotify && (currentTime - timeExpectToNotify) < 60000){
			isRightTime = true;
		} else if (currentTime == timeExpectToNotify){
			isRightTime = true;
		} else if (currentTime < timeExpectToNotify && (timeExpectToNotify - currentTime) < 60000){
			isRightTime = true;
		}
		
		return isRightTime;
		
	}
	
	public static boolean isTimeSendForFirst(long timeExpectToNotify){
		
		// Get current time
		long currentTime = System.currentTimeMillis();
		
		boolean isRightTime = false;
		
		if (currentTime > timeExpectToNotify && (currentTime - timeExpectToNotify) < 150000){
			isRightTime = true;
		} else if (currentTime == timeExpectToNotify){
			isRightTime = true;
		} else if (currentTime < timeExpectToNotify && (timeExpectToNotify - currentTime) < 150000){
			isRightTime = true;
		}
		
		return isRightTime;
		
	}
	
	public static boolean isRightTimeToSend(SettingSms settingSms , Date lastActionSms , String type){
		
		// Get last checkin time
 		long lastCheckin = lastActionSms.getTime();
		
		boolean isRightTime = false;
		
		// Get time type
		long gapNotify = getGapNotify(settingSms);

		long timeExpectToNotify = lastCheckin + gapNotify;
		
		if (NotificationConstant.TIME_REPEAT.equalsIgnoreCase(type)){
			isRightTime = isTimeSendForFirst(timeExpectToNotify);
		} else {
			isRightTime = isTimeSend(timeExpectToNotify);
		}

		
		return isRightTime;
	}
	
	public static boolean isQueueActive(Date lastTime , SettingSms settingSms){
		
		// Get time type
		long gapNotify = getGapNotify(settingSms);
		long lastTimeSend = lastTime.getTime();
		long currentTime = System.currentTimeMillis();
		
		if(currentTime - lastTimeSend > gapNotify){
			return false;
		}
		return true;
	}

}
