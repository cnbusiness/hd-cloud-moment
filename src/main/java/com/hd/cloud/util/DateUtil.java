package com.hd.cloud.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @ClassName: DateUtil
 * @Description: 时间util
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company hadoop-tech
 * @date 2018年4月12日 下午5:02:48
 *
 */
public final class DateUtil {

	/**
	 * 
	 * 
	 * @Title: getCurrentTime
	 * @param:
	 * @Description:  获取当前时间  精确到秒
	 * @return String
	 */
	public static String getCurrentSystemTimeInSecond() { // REMOVE
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.getTime());
		return String.valueOf(calendar.getTimeInMillis() / 1000);
	}

	/**
	 * 
	 * @Title: getYesterdayTime
	 * @param:
	 * @Description: 获取昨天同一时间的时间
	 * @return int
	 */
	public static String getYesterdayTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return String.valueOf(calendar.getTimeInMillis() / 1000);
	}

	/**
	 * 
	 * @Title: getYesterday
	 * @param:
	 * @Description: 获取昨天日期
	 * @return int
	 */
	public static String getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
		return String.valueOf(yesterday);
	}

	public static Date addMonths(Date src, int addMonths) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(src);
		calendar.add(Calendar.MONTH, addMonths);
		return calendar.getTime();

	}

	public static Date addMonths(int addMonths) {
		return addMonths(new Date(), addMonths);

	}

	public static Date addDays(Date src, int addDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(src);
		calendar.add(Calendar.DATE, addDays);
		return calendar.getTime();

	}

	public static Date addDays(int addDays) {
		return addDays(new Date(), addDays);

	}

	public static Date addHours(Date src, int addHours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(src);
		calendar.add(Calendar.HOUR, addHours);
		return calendar.getTime();

	}

	public static Date addHours(int addHours) {
		return addHours(new Date(), addHours);

	}

	public static Date addSeconds(Date src, int addSeconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(src);
		calendar.add(Calendar.SECOND, addSeconds);
		return calendar.getTime();

	}

	public static Date addSeconds(int addSeconds) {
		return addSeconds(new Date(), addSeconds);

	}

	/**
	 * 格式化日期输出
	 *
	 * @param src
	 *            待格式化的日期变量
	 * @param formatPattern
	 *            输出的样式 <blockquote>
	 *            <table border=0 cellspacing=3 cellpadding=0 summary="Examples of
	 *            date and time patterns interpreted in the U.S. locale">
	 *            <tr bgcolor="#ccccff">
	 *            <th align=left>Date and Time Pattern
	 *            <th align=left>Result
	 *            <tr>
	 *            <td><code>"yyyy.MM.dd G 'at' HH:mm:ss z"</code>
	 *            <td><code>2001.07.04 AD at 12:08:56 PDT</code>
	 *            <tr bgcolor="#eeeeff">
	 *            <td><code>"EEE, MMM d, ''yy"</code>
	 *            <td><code>Wed, Jul 4, '01</code>
	 *            <tr>
	 *            <td><code>"h:mm a"</code>
	 *            <td><code>12:08 PM</code>
	 *            <tr bgcolor="#eeeeff">
	 *            <td><code>"hh 'o''clock' a, zzzz"</code>
	 *            <td><code>12 o'clock PM, Pacific Daylight Time</code>
	 *            <tr>
	 *            <td><code>"K:mm a, z"</code>
	 *            <td><code>0:08 PM, PDT</code>
	 *            <tr bgcolor="#eeeeff">
	 *            <td><code>"yyyyy.MMMMM.dd GGG hh:mm aaa"</code>
	 *            <td><code>02001.July.04 AD 12:08 PM</code>
	 *            <tr>
	 *            <td><code>"EEE, d MMM yyyy HH:mm:ss Z"</code>
	 *            <td><code>Wed, 4 Jul 2001 12:08:56 -0700</code>
	 *            <tr bgcolor="#eeeeff">
	 *            <td><code>"yyMMddHHmmssZ"</code>
	 *            <td><code>010704120856-0700</code>
	 *            <tr>
	 *            <td><code>"yyyy-MM-dd'T'HH:mm:ss.SSSZ"</code>
	 *            <td><code>2001-07-04T12:08:56.235-0700</code>
	 *            </table>
	 *            </blockquote>
	 * @return 输出后的日期字符串
	 */
	public static String formatDate(Date src, String formatPattern) {
		if (src == null) {
			return "";
		}
		DateFormat fmt = new SimpleDateFormat(formatPattern);
		return fmt.format(src);
	}

	public static String formatDate(Date src) {
		return formatDate(src, "yyyy-MM-dd HH:mm:ss");
	}

	public static String formatDate() {
		return formatDate(new Date(), "yyyy-MM-dd");
	}

	public static String formatDate(String formatPattern) {
		return formatDate(new Date(), formatPattern);
	}

	public static Date getDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month - 1, day);
		return calendar.getTime();

	}

	/**
	 * @Title: getStampTime
	 * @param: long
	 *             time
	 * @Description: 将时间戳转换为HH:mm
	 * @return String
	 */
	public static String getStampTime(long time) {
		String mTmepString = "";
		if (time == 0) {
			return mTmepString;
		}
		String format = "HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		mTmepString = sdf.format(new Date(Long.valueOf(time)));
		return mTmepString;
	}

	/**
	 * 将时间转换为long类型
	 * 
	 * @Title: getLongTime
	 * @param:
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return long
	 */
	public static long getLongTime(String time) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}

	/**
	 * 
	 * @Title: getTimesmorning
	 * @param:
	 * @Description: 获取当天0点的时间
	 * @return int 秒
	 */
	public static int getTimesMorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (int) (cal.getTimeInMillis() / 1000);
	}

	/**
	 * 
	 * @Title: currentTimeBySecond
	 * @param:
	 * @Description: 针对这个函数SEC_TO_TIME产生的秒，显示具体的时间
	 * @return String
	 */
	public static String currentTimeBySecond(int time) {
		return DateUtil.formatDate(new Date((time + DateUtil.getTimesMorning()) * 1000));
	}

	/**
	 * 获取两个时间戳的日期差
	 * 
	 * @Title: daysBetween
	 * @param: long
	 *             sTime,long eTime
	 * @Description:
	 * @return int
	 */
	public static int daysBetween(long sTime, long eTime) {
		long between_days = (eTime - sTime) / (3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 把毫秒转化成日期
	 * 
	 * @param dateFormat(日期格式，例如：MM/
	 *            dd/yyyy HH:mm:ss)
	 * @param millSec(毫秒数)
	 * @return
	 */
	public static String transferLongToDate(String dateFormat, Long millSec) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = new Date(millSec);
		return sdf.format(date);
	}
}