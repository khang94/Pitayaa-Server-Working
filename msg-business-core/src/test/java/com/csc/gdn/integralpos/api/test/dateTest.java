package com.csc.gdn.integralpos.api.test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class dateTest {
	public static void main(String[] args){
		Date date = new Date();
		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		date = cal.getTime();
		System.out.println(date);
	}
}
