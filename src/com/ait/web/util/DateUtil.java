package com.ait.web.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

public class DateUtil {

	private static DateUtil instance;
	private static DateFormat format;

	public DateUtil() {
		if (instance == null) {
			getInstance();
		}
	}

	public static DateUtil getInstance() {
		return new DateUtil();
	}

	/**
	 * get String Sysdate
	 * 
	 * @return
	 */
	public static String getSysdateStr() {
		String timeFormat = "yyyy-MM-dd";
		SimpleDateFormat timeFormatter = new SimpleDateFormat(timeFormat);
		String sDate = timeFormatter.format(Calendar.getInstance().getTime());
		return sDate;
	}


	/**
	 * 指定格式的当前日期
	 * 
	 * @return
	 */
	public static String getSysdateStr(String timeFormat) {
		SimpleDateFormat timeFormatter = new SimpleDateFormat(timeFormat);
		String sDate = timeFormatter.format(Calendar.getInstance().getTime());
		return sDate;
	}
	
	/**
	 * get String current month
	 * 
	 * @return
	 */
	public static String getCurrentYearStr() {
		String timeFormat = "yyyy";
		SimpleDateFormat timeFormatter = new SimpleDateFormat(timeFormat);
		String sDate = timeFormatter.format(Calendar.getInstance().getTime());
		return sDate;
	}
	/**
	 * get String current month
	 * 
	 * @return
	 */
	public static String getCurrentMonthStr() {
		String timeFormat = "yyyyMM";
		SimpleDateFormat timeFormatter = new SimpleDateFormat(timeFormat);
		String sDate = timeFormatter.format(Calendar.getInstance().getTime());
		return sDate;
	}
	
	/**
	 * get String current month
	 * 
	 * @return
	 */
	public static String getCurrentMonthStrFormat() {
		String timeFormat = "yyyy-MM";
		SimpleDateFormat timeFormatter = new SimpleDateFormat(timeFormat);
		String sDate = timeFormatter.format(Calendar.getInstance().getTime());
		return sDate;
	}
	
	/**
	 * get String current month
	 * 
	 * @return
	 */
	public static String getCurrentMonthStrFormatMY() {
		String timeFormat = "MMyyyy";
		SimpleDateFormat timeFormatter = new SimpleDateFormat(timeFormat);
		String sDate = timeFormatter.format(Calendar.getInstance().getTime());
		return sDate;
	}
	
	/**
	 * get String last month
	 * 
	 * @return
	 */
	public static String getLastMonthStr() {
		String timeFormat = "yyyyMM";
		SimpleDateFormat timeFormatter = new SimpleDateFormat(timeFormat);
		Calendar cal = Calendar.getInstance();
        cal.setTime(Calendar.getInstance().getTime());
        cal.add(Calendar.MONTH, -1);
        String sDate = timeFormatter.format(cal.getTime());
		return sDate;
	}
	
	/**
	 * get String last month(format mmyyyy)
	 * 
	 * @return
	 */
	public static String getLastMonthMYStr() {
		String timeFormat = "MMyyyy";
		SimpleDateFormat timeFormatter = new SimpleDateFormat(timeFormat);
		Calendar cal = Calendar.getInstance();
        cal.setTime(Calendar.getInstance().getTime());
        cal.add(Calendar.MONTH, -1);
        String sDate = timeFormatter.format(cal.getTime());
		return sDate;
	}

	public static GregorianCalendar ParseGregorianCalendar(String dateStr) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.clear();
		if (dateStr != null) {
			if (dateStr.length() == 10) {
				// yyyy-MM-dd
				gregorianCalendar.set(
						Integer.parseInt(dateStr.substring(0, 4)), Integer
								.parseInt(dateStr.substring(5, 7)) - 1, Integer
								.parseInt(dateStr.substring(8, 10)), 0, 0, 0);
			} else if (dateStr.length() == 13) {
				// yyyy-MM-dd hh
				gregorianCalendar.set(
						Integer.parseInt(dateStr.substring(0, 4)), Integer
								.parseInt(dateStr.substring(5, 7)) - 1, Integer
								.parseInt(dateStr.substring(8, 10)), Integer
								.parseInt(dateStr.substring(11, 13)), 0, 0);
			} else if (dateStr.length() == 16) {
				// yyyy-MM-dd hh:mm
				gregorianCalendar.set(
						Integer.parseInt(dateStr.substring(0, 4)), Integer
								.parseInt(dateStr.substring(5, 7)) - 1, Integer
								.parseInt(dateStr.substring(8, 10)), Integer
								.parseInt(dateStr.substring(11, 13)), Integer
								.parseInt(dateStr.substring(14, 16)), 0);
			} else if (dateStr.length() >= 19) {
				// yyyy-MM-dd hh:mm:ss
				gregorianCalendar.set(
						Integer.parseInt(dateStr.substring(0, 4)), Integer
								.parseInt(dateStr.substring(5, 7)) - 1, Integer
								.parseInt(dateStr.substring(8, 10)), Integer
								.parseInt(dateStr.substring(11, 13)), Integer
								.parseInt(dateStr.substring(14, 16)), Integer
								.parseInt(dateStr.substring(17, 19)));
			}
		}
		return gregorianCalendar;
	}

	public static long DateCross(GregorianCalendar fromDate1,
			GregorianCalendar toDate1, GregorianCalendar fromDate2,
			GregorianCalendar toDate2, String type) {
		long length = 0;
		GregorianCalendar fromDate = fromDate1;
		GregorianCalendar toDate = toDate1;

		if (fromDate2.after(fromDate1))
			fromDate = fromDate2;
		if (toDate2.before(toDate1))
			toDate = toDate2;
		if (fromDate.after(toDate))
			length = 0;
		else
			length = DateDiff(fromDate, toDate, type);
		Logger.getLogger(DateUtil.class).debug(
				"length : " + String.valueOf(length));
		return length;
	}

	// 返回两个日期之间的差
	public static long DateDiff(GregorianCalendar date1,
			GregorianCalendar date2, String type) {
		long difference = 0;
		long milliseconds = date2.getTimeInMillis() - date1.getTimeInMillis();
		type = StringUtil.checkNull(type);
		if (type.equalsIgnoreCase("YEAR")) {
			difference = date2.get(GregorianCalendar.YEAR)
					- date1.get(GregorianCalendar.YEAR);
			if (DateAdd(date1, "YEAR",
					Integer.parseInt(String.valueOf(difference))).after(date2))
				difference = difference - 1;
		} else if (type.equalsIgnoreCase("MONTH")) {
			difference = date2.get(GregorianCalendar.MONTH)
					- DateAdd(
							date1,
							"YEAR",
							Integer.parseInt(String.valueOf(DateDiff(date1,
									date2, "YEAR")))).get(
							GregorianCalendar.MONTH);
			if (DateAdd(
					date1,
					"MONTH",
					Integer.parseInt(String.valueOf(DateDiff(date1, date2,
							"YEAR")
							* 12 + difference))).after(date2))
				difference = difference - 1;
		} else if (type.equalsIgnoreCase("DAY"))
			difference = milliseconds / 1000 / 60 / 60 / 24;
		else if (type.equalsIgnoreCase("HOUR"))
			difference = milliseconds / 1000 / 60 / 60;
		else if (type.equalsIgnoreCase("MINUTE"))
			difference = milliseconds / 1000 / 60;
		else if (type.equalsIgnoreCase("SECOND"))
			difference = milliseconds / 1000;
		else if (type.equalsIgnoreCase("MILLISECOND"))
			difference = milliseconds;
		return difference;
	}

	// 返回某日期加减一个数值后的结果日期
	public static GregorianCalendar DateAdd(GregorianCalendar date,
			String type, int integer) {
		GregorianCalendar dateAfter = date;
		type = StringUtil.checkNull(type);
		if (type.equalsIgnoreCase("YEAR"))
			dateAfter.add(Calendar.YEAR, integer);
		else if (type.equalsIgnoreCase("MONTH"))
			dateAfter.add(Calendar.MONTH, integer);
		else if (type.equalsIgnoreCase("DAY"))
			dateAfter.add(Calendar.DAY_OF_MONTH, integer);
		else if (type.equalsIgnoreCase("WEEK"))
			dateAfter.add(Calendar.WEEK_OF_YEAR, integer);
		else if (type.equalsIgnoreCase("WEEK_OF_YEAR"))
			dateAfter.add(GregorianCalendar.WEEK_OF_YEAR, integer);
		else if (type.equalsIgnoreCase("WEEK_OF_MONTH"))
			dateAfter.add(GregorianCalendar.WEEK_OF_MONTH, integer);
		else if (type.equalsIgnoreCase("HOUR"))
			dateAfter.add(GregorianCalendar.HOUR, integer);
		else if (type.equalsIgnoreCase("MINUTE"))
			dateAfter.add(GregorianCalendar.MINUTE, integer);
		else if (type.equalsIgnoreCase("SECOND"))
			dateAfter.add(GregorianCalendar.SECOND, integer);
		else if (type.equalsIgnoreCase("MILLISECOND"))
			dateAfter.add(GregorianCalendar.MILLISECOND, integer);
		return dateAfter;
	}
	/**
	 * get 当前月最后一天
	 * 
	 * @return
	 */
	public static String getCurrentMonthLastDayStr() {
		String timeFormat = "yyyy-MM-dd";
		SimpleDateFormat timeFormatter = new SimpleDateFormat(timeFormat);
		Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
		String sDate = timeFormatter.format(ca.getTime());
		return sDate;
	}

	/**
	 * get String last month
	 * 
	 * @return
	 */
	public static String getMonthStrAgo(int month,String format) {
		SimpleDateFormat timeFormatter = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
        cal.setTime(Calendar.getInstance().getTime());
        cal.add(Calendar.MONTH, - month);
        String sDate = timeFormatter.format(cal.getTime());
		return sDate;
	}	
	

	/**
	 * get String last month
	 * 
	 * @return
	 */
	public static String getDayStrAgo(int day,String format) {
		SimpleDateFormat timeFormatter = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
        cal.setTime(Calendar.getInstance().getTime());
        cal.add(Calendar.DAY_OF_MONTH, - day);
        String sDate = timeFormatter.format(cal.getTime());
		return sDate;
	}	

	/**
	 * get String last month
	 * 
	 * @return
	 */
	public static String getDayStrAgoDay(int day,String format) {
		SimpleDateFormat timeFormatter = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
        cal.setTime(Calendar.getInstance().getTime());
        cal.add(Calendar.DATE, - day);
        String sDate = timeFormatter.format(cal.getTime());
		return sDate;
	}
	
	/**
	 * convert date of string format
	 * 
	 * @return
	 */
	public static String convertStringDateFormat (String date, String initDateFormat, String endDateFormat){
		String parsedDate = "";
		if (date == null || "".equals(date)) {
			return parsedDate;
		} else {
			try {
				Date initDate = new SimpleDateFormat(initDateFormat).parse(date);
			    SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
			    parsedDate = formatter.format(initDate);
			} catch (ParseException e) {
				e.printStackTrace();
				return "Date format error";
			}
			return parsedDate;
		}
	}
}
