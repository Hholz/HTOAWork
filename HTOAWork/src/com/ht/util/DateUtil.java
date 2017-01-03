package com.ht.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/*
	 * 判断该日期是周几
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = {"周7", "周1", "周2", "周3", "周4", "周5", "周6"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
		w = 0;
		return weekDays[w];
	}
	public static String DateToString(Date dt){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(dt);
	}
}
