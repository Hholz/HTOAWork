package com.ht.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/*
	 * �жϸ��������ܼ�
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = {"��7", "��1", "��2", "��3", "��4", "��5", "��6"};
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
