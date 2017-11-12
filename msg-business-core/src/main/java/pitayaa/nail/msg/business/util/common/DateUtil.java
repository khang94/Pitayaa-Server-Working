package pitayaa.nail.msg.business.util.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import pitayaa.nail.msg.business.constant.ActionConstant;



public class DateUtil {
	private DateUtil() {
		super();
	}

	/** The Constant DATE_FORMAT_NOW. */
	public static final String DATE_FORMAT_NOW = "dd/MM/yyyy HH:mm:ss";
	public static final String DATE_FORMAT_DMY = "MM/dd/yyyy HH:mm:ss";

	/** The Constant SECOND. */
	public static final long SECOND = 1000;

	/** The Constant MINUTE. */
	public static final long MINUTE = SECOND * 60;

	/** The Constant HOUR. */
	public static final long HOUR = MINUTE * 60;

	/** The Constant DAY. */
	public static final long DAY = HOUR * 24;

	public static Date now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		try {
			return sdf.parse(sdf.format(cal.getTime()));
		} catch (ParseException e) {
			return new Date();
		}
	}

	public static Date now(String formatDate) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
		try {
			return sdf.parse(sdf.format(cal.getTime()));
		} catch (ParseException e) {
			return new Date();
		}
	}

	public static String format(Date d, String format) {
		if (d == null) {
			return " ";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(d);
	}

	public static String format(Date d) {
		return format(d, DATE_FORMAT_NOW);
	}
	public static Date parse(String str, String format) throws Exception{
		DateFormat sdf = new SimpleDateFormat(format);
		
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			throw e;
		}
	}
	
	public static Date parse(String str) throws Exception{
		return parse(str, DATE_FORMAT_DMY);
	}
	

	public static int getHour(Date date) {
		String hour = null;
		DateFormat f = new SimpleDateFormat("HH");
		try {
			hour = f.format(date);
			return Integer.parseInt(hour);
		} catch (Exception e) {
			return -1;
		}
	}

	public static int getMinute(Date date) {
		String minute = null;
		DateFormat f = new SimpleDateFormat("mm");
		try {
			minute = f.format(date);
			return Integer.parseInt(minute);
		} catch (Exception e) {
			return -1;
		}
	}

	public static String getAMPM(Date date) {
		DateFormat f = new SimpleDateFormat("a");
		try {
			return f.format(date).toUpperCase();
		} catch (Exception e) {
			return "";
		}
	}

	public static int getMonth(Date date) {
		String month = null;
		DateFormat f = new SimpleDateFormat("MM");
		try {
			month = f.format(date);
			return Integer.parseInt(month);
		} catch (Exception e) {
			return -1;
		}
	}

	public static int getYear(Date date) {
		String year = null;
		DateFormat f = new SimpleDateFormat("yyyy");
		try {
			year = f.format(date);
			return Integer.parseInt(year);
		} catch (Exception e) {
			return -1;
		}
	}

	public static int getDay(Date date) {
		String day = null;
		DateFormat f = new SimpleDateFormat("dd");
		try {
			day = f.format(date);
			return Integer.parseInt(day);
		} catch (Exception e) {
			return -1;
		}
	}

	public static int compareTo(Date date1, Date date2) {
		return compareTo(date1, date2, false);
	}

	public static int compareTo(Date date1, Date date2,
			boolean ignoreMilliseconds) {

		if ((date1 != null) && (date2 == null)) {
			return -1;
		} else if ((date1 == null) && (date2 != null)) {
			return 1;
		} else if ((date1 == null) && (date2 == null)) {
			return 0;
		}

		long time1 = date1.getTime();
		long time2 = date2.getTime();

		if (ignoreMilliseconds) {
			time1 = time1 / SECOND;
			time2 = time2 / SECOND;
		}

		if (time1 == time2) {
			return 0;
		} else if (time1 < time2) {
			return -1;
		} else {
			return 1;
		}
	}

	public static int compare(Date date1, Date date2) {

		if ((date1 != null) && (date2 == null)) {
			return -1;
		} else if ((date1 == null) && (date2 != null)) {
			return 1;
		} else if ((date1 == null) && (date2 == null)) {
			return 0;
		}

		long time1 = date1.getTime() / HOUR;
		long time2 = date2.getTime() / HOUR;

		if (time1 == time2) {
			return 0;
		} else if (time1 < time2) {
			return -1;
		} else {
			return 1;
		}
	}

	public static int compareTwoDate(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		int year = cal1.get(Calendar.YEAR);
		int month = cal1.get(Calendar.MONTH);
		int day = cal1.get(Calendar.DATE);
		int hour = 0;
		int minute = 0;
		int second = 0;
		cal1.set(year, month, day, hour, minute, second);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int year2 = cal2.get(Calendar.YEAR);
		int month2 = cal2.get(Calendar.MONTH);
		int day2 = cal2.get(Calendar.DATE);
		cal2.set(year2, month2, day2, 0, 0, 0);

		if (cal1.getTimeInMillis() == cal2.getTimeInMillis()) {
			return 0;
		} else if (cal1.getTimeInMillis() < cal2.getTimeInMillis()) {
			return -1;
		} else {
			return 1;
		}
	}

	public static boolean equals(Date date1, Date date2) {
		if (compareTo(date1, date2) == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean equals(Date date1, Date date2,
			boolean ignoreMilliseconds) {

		if (!ignoreMilliseconds) {
			return equals(date1, date2);
		}

		long time1 = 0;

		if (date1 != null) {
			time1 = date1.getTime() / SECOND;
		}

		long time2 = 0;

		if (date2 != null) {
			time2 = date2.getTime() / SECOND;
		}

		if (time1 == time2) {
			return true;
		} else {
			return false;
		}
	}

	public static long getMinutesBetween(Date startDate, Date endDate) {

		TimeZone timeZone = TimeZone.getTimeZone("GMT");
		int offset = timeZone.getRawOffset();

		Calendar startCal = new GregorianCalendar(timeZone);
		startCal.setTime(startDate);
		startCal.add(Calendar.MILLISECOND, offset);

		Calendar endCal = new GregorianCalendar(timeZone);
		endCal.setTime(endDate);
		endCal.add(Calendar.MILLISECOND, offset);

		long milis1 = startCal.getTimeInMillis();
		long milis2 = endCal.getTimeInMillis();

		long diff = milis2 - milis1;
		long minutesBetween = diff / (60 * 1000);
		return minutesBetween;
	}

	public static int getDaysBetween(Date startDate, Date endDate) {

		TimeZone timeZone = TimeZone.getTimeZone("GMT");

		int offset = timeZone.getRawOffset();

		Calendar startCal = new GregorianCalendar(timeZone);

		startCal.setTime(startDate);
		startCal.add(Calendar.MILLISECOND, offset);

		Calendar endCal = new GregorianCalendar(timeZone);

		endCal.setTime(endDate);
		endCal.add(Calendar.MILLISECOND, offset);

		int daysBetween = 0;

		// System.out.println("numDate = " + daysBetween(startDate, endDate));

		while (beforeByDay(startCal.getTime(), endCal.getTime())) {
			startCal.add(Calendar.DAY_OF_MONTH, 1);

			daysBetween++;
		}

		return daysBetween;
	}

	public static boolean beforeByDay(Date date1, Date date2) {
		long millis1 = _getTimeInMillis(date1);
		long millis2 = _getTimeInMillis(date2);

		if (millis1 < millis2) {
			return true;
		} else {
			return false;
		}
	}

	private static long _getTimeInMillis(Date date) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		int hour = 0;
		int minute = 0;
		int second = 0;

		cal.set(year, month, day, hour, minute, second);

		long millis = cal.getTimeInMillis() / DAY;

		return millis;
	}

	public static int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

	public static boolean isNewWeek(Date currentDate, Date lastCheckInTime) {
		Date firstDayOfWeek = getFirstDayOfWeek(currentDate);
		if (compareTwoDate(lastCheckInTime, firstDayOfWeek) == -1) {
			return true;
		}
		return false;
	}

	public static Date getFirstDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dow = cal.get(Calendar.DAY_OF_WEEK);
		// System.out.println(dow);
		cal.add(Calendar.DAY_OF_YEAR, (dow * -1) + 2);
		return cal.getTime();
	}
	
	public static int compareDate(Date firstArg, Date secondArg)
			throws Exception {
		Calendar cal = Calendar.getInstance();
		Date _dateF = null, _dateS = null;

		cal.setTime(firstArg);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		_dateF = cal.getTime();

		cal.clear();
		cal.setTime(secondArg);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		_dateS = cal.getTime();

		return _dateF.compareTo(_dateS);
	}

	public static int compareDateWithoutTime(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.SECOND, 0);
		cal2.set(Calendar.MILLISECOND, 0);
		if (cal1.equals(cal2)) {
			return 0;
		} else if (cal1.after(cal2)) {
			return 1;
		} else {
			return -1;
		}
	}

	public static Date getCurrentGMTDate() {
		Date now = new Date();
		Calendar calendar = new GregorianCalendar();
		int offset = calendar.getTimeZone().getOffset(now.getTime());
		Date gmtDate = new Date(now.getTime() - offset);
		return gmtDate;
	}

	public static Calendar getCurrentGMTDate(int GMT) {
		String gmtTxt = "GMT" + String.valueOf(GMT);
		Calendar mbCal = new GregorianCalendar(TimeZone.getTimeZone(gmtTxt));
		Date now = new Date();
		mbCal.setTimeInMillis(now.getTime());

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, mbCal.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, mbCal.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, mbCal.get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, mbCal.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, mbCal.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, mbCal.get(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, mbCal.get(Calendar.MILLISECOND));

		return cal;
	}

	public static Date addDate(Date input, int number){
		Calendar cal = Calendar.getInstance();
		cal.setTime(input);

		cal.add(Calendar.DATE, number);

		return cal.getTime();
	}
	
	public static Date addMonth(Date input, int month){
		Calendar cal = Calendar.getInstance();
		cal.setTime(input);

		cal.add(Calendar.MONTH, month);

		return cal.getTime();
	}
	
	public static Date addYear(Date input, int year){
		Calendar cal = Calendar.getInstance();
		cal.setTime(input);

		cal.add(Calendar.YEAR, year);

		return cal.getTime();
	}

	public static Date addHour(Date input, int number) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(input);

		cal.add(Calendar.HOUR, number);

		return cal.getTime();
	}
	
	/**
	 * dua thoi gian tu 1 timezone bat ky ve timezone he thong
	 * 
	 * @param date
	 * @param fromTimezone
	 * @return
	 * @throws Exception
	 */
	public static Date convertDateWithTimezone(String date, String fromTimezone) throws Exception{
		if(StringUtil.isNullOrEmpty(date)){
			throw new IllegalArgumentException();
		}
		
		DateFormat indfm = new SimpleDateFormat(ActionConstant.JSON_DATE_FORMAT);
		
		if(! StringUtil.isNullOrEmpty(fromTimezone)){
			indfm.setTimeZone(TimeZone.getTimeZone(fromTimezone));
		}
		
		return indfm.parse(date);
	}
	
	/**
	 * format thoi gian he thong sang mot timezone bat ky
	 * 
	 * @param date
	 * @param toTimezone
	 * @return
	 * @throws Exception
	 */
	public static String formatDateWithTimezone(Date date, String toTimezone) throws Exception{
		if(date == null){
			return null;
		}
		
		if(StringUtil.isNullOrEmpty(toTimezone)){
			return format(date, ActionConstant.JSON_DATE_FORMAT);
		} else {
			Calendar localTime = Calendar.getInstance();
			localTime.setTimeZone(TimeZone.getDefault()); 
			localTime.setTime(date);
			
			
	        Calendar indiaTime = new GregorianCalendar(
	                TimeZone.getTimeZone(toTimezone));
	        indiaTime.setTimeInMillis(localTime.getTimeInMillis());
	        
			DateFormat indfm = new SimpleDateFormat(ActionConstant.JSON_DATE_FORMAT);
			indfm.setTimeZone(TimeZone.getTimeZone(toTimezone));
			String str = indfm.format(date);
			DateFormat format2 = new SimpleDateFormat(ActionConstant.JSON_DATE_FORMAT);

			String str2 = format2.format(indiaTime.getTime());
			
			return indfm.format(date);
		}
	}
	
	public static String parseIOsTimezone(String timezone) throws Exception {
		// dinh dang timezone +_hh.mm
		if(StringUtil.isNullOrEmpty(timezone)){
			throw new IllegalArgumentException("timezone is null or empty");
		}
		
		String firstChar = timezone.substring(0, 1);
		String secondChar = timezone.substring(1, timezone.indexOf('.'));
		String thirstChar = timezone.substring(timezone.indexOf('.') + 1);
		
		StringBuilder output = new StringBuilder("GMT");
		if("-+".indexOf(firstChar) == -1){
			throw new IllegalArgumentException("timezone is wrong format");
		} else {
			output.append(firstChar);
		}
		
		if(StringUtil.isNumber(secondChar)){
			if(secondChar.length() == 1){
				output.append("0").append(secondChar);
			} else if (secondChar.length() == 2){
				output.append(secondChar);
			} else {
				throw new IllegalArgumentException("timezone is wrong format");
			}
		} else {
			throw new IllegalArgumentException("timezone is wrong format");
		}
		
		Integer value = null;
		if(thirstChar.length() == 1){
			value = (int)(Integer.parseInt(thirstChar) * 0.1 * 60);
		} else if (thirstChar.length() == 2){
			value = (int)(Integer.parseInt(thirstChar) * 0.01 * 60);
		} else {
			throw new IllegalArgumentException("timezone is wrong format");
		}
		
		if(value < 10){
			output.append(":").append("0").append(value);
		} else if (value < 60){
			output.append(":").append(value);
		} else {
			throw new IllegalArgumentException("timezone is wrong format");
		}
		
		return output.toString();
	}
	
	/**
	 * dua thoi gian tu 1 timezone bat ky ve timezone he thong
	 * 
	 * @param date
	 * @param fromTimezone
	 * @return
	 * @throws Exception
	 */
	public static Date convertToSysDate(String date, String fromTimezone) throws Exception{
		if(StringUtil.isNullOrEmpty(date)){
			throw new IllegalArgumentException();
		}
		
		DateFormat indfm = new SimpleDateFormat(ActionConstant.JSON_DATE_FORMAT);
		
		if(! StringUtil.isNullOrEmpty(fromTimezone)){
			indfm.setTimeZone(TimeZone.getTimeZone(fromTimezone));
		}
		Date tet = indfm.parse(date); 
        Calendar serverTime = new GregorianCalendar(
                TimeZone.getDefault());
        serverTime.setTimeInMillis(indfm.parse(date).getTime());
        Date result = serverTime.getTime();
        
		return serverTime.getTime();
	}
	
	/**
	 * format thoi gian he thong sang mot timezone bat ky
	 * 
	 * @param date
	 * @param toTimezone
	 * @return
	 * @throws Exception
	 */
	public static String formatSysDateWithOtherTimezone(Date date, String toTimezone) throws Exception{
		if(date == null){
			return null;
		}
		
		if(StringUtil.isNullOrEmpty(toTimezone)){
			return format(date, ActionConstant.JSON_DATE_FORMAT);
		} else {
			DateFormat indfm = new SimpleDateFormat(ActionConstant.JSON_DATE_FORMAT);
			indfm.setTimeZone(TimeZone.getTimeZone(toTimezone));
			
			return indfm.format(date);
		}
	}
	
	public static String formatSysDateWithOtherTimezone(Date date, String strFormat,
			String toTimezone) throws Exception {
		String format = StringUtil.isNullOrEmpty(strFormat) ? ActionConstant.JSON_DATE_FORMAT
				: strFormat;
		
		if (date == null) {
			return null;
		}

		if (StringUtil.isNullOrEmpty(toTimezone)) {
			return format(date, format);
		} else {
			DateFormat indfm = new SimpleDateFormat(format);
			indfm.setTimeZone(TimeZone.getTimeZone(toTimezone));

			return indfm.format(date);
		}
	}
/*	public static void main(String[] args) {
		try {
//			String from ="10-24-2015";
//			Date date = new Date();
//			System.out.println(date);
//			Date date2 = DateUtil.parse(from,"MM-dd-yyyy");
//			System.out.println(date2);
//			from =DateUtil.format(date2, "yyyy-MM-dd ");
//			System.out.println(from);
			System.out.println(new Date());
			long start = System.currentTimeMillis();
			System.out.println(DateUtil.parse(DateUtil.formatDateWithTimezone(new Date(start), "GMT"+(int)Double.parseDouble("-5.0")),"yyyy-MM-dd HH:mm:ss").getTime());
			System.out.println(DateUtil.formatDateWithTimezone(new Date(), "GMT-5"));
			System.out.println(DateUtil.formatSysDateWithOtherTimezone(new Date(), "GMT-5"));
			Calendar cal =		DateUtil.getCurrentGMTDate((int)Double.parseDouble("-5.0"));
			System.out.println(cal.getTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}*/
	
}
