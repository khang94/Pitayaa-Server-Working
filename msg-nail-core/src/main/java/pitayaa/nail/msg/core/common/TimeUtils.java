package pitayaa.nail.msg.core.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import pitayaa.nail.msg.core.customer.service.CustomerServiceImpl;

@Service
public class TimeUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TimeUtils.class);
	
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
	
	
}
