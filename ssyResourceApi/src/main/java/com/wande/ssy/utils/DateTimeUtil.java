package com.wande.ssy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * 日期,时间戳工具类
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年2月8日 下午2:02:43
 */
public class DateTimeUtil {
	
	public static long ONE_DAY    = 24*60*60*1000;	//一天的时间(毫秒)
	public static long ONE_HOUR   = 60*60*1000;		//一小时时间(毫秒)
	public static long ONE_MINUTE = 60*1000;			//一分钟时间(毫秒)
	public static long ONE_SECOND = 1000;			//一秒种时间(毫秒)
	
	/**
	 * 匹配输入的时间字符串是否有效,返回对应的格式
	 *
	 * @param dateStr 时间字符串
	 * @return
	 */
	public static String matches(String dateStr) {
		String formatStr = "";
		if (dateStr.matches("\\d{4}\\-\\d{1,2}\\-\\d{1,2}\\s+\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}\\.\\d{1,3}")) {
			formatStr = "yyyy-MM-dd HH:mm:ss.SSS";
		} else if (dateStr.matches("\\d{4}\\-\\d{1,2}\\-\\d{1,2}\\s+\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}")) {
			formatStr = "yyyy-MM-dd HH:mm:ss";
		} else if (dateStr.matches("\\d{4}\\-\\d{1,2}\\-\\d{1,2}\\s+\\d{1,2}\\:\\d{1,2}")) {
			formatStr = "yyyy-MM-dd HH:mm";
		} else if (dateStr.matches("\\d{4}\\-\\d{1,2}\\-\\d{1,2}\\s+\\d{1,2}")) {
			formatStr = "yyyy-MM-dd HH";
		} else if (dateStr.matches("\\d{4}\\-\\d{1,2}\\-\\d{1,2}")) {
			formatStr = "yyyy-MM-dd";
		} else if (dateStr.matches("\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\s+\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}\\.\\d{1,3}")) {
			formatStr = "yyyy/MM/dd HH:mm:ss.SSS";
		} else if (dateStr.matches("\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\s+\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}")) {
			formatStr = "yyyy/MM/dd HH:mm:ss";
		} else if (dateStr.matches("\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\s+\\d{1,2}\\:\\d{1,2}")) {
			formatStr = "yyyy/MM/dd HH:mm";
		} else if (dateStr.matches("\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\s+\\d{1,2}")) {
			formatStr = "yyyy/MM/dd HH";
		} else if (dateStr.matches("\\d{4}\\/\\d{1,2}\\/\\d{1,2}")) {
			formatStr = "yyyy/MM/dd";
		}
		return formatStr;
	}
    
    /**
     * 按照格式, 格式化当前日期(yyyy-MM-dd HH:mm:ss.SSS)
     * 
     * @param format	日期格式
     * @return
     */
    public static String formatDate(String format){
		return formatDate(new Date(), format);
    }
    
    /**
     * 按照格式, 格式化日期(yyyy-MM-dd HH:mm:ss.SSS)
     * 
     * @param date		Date对象
     * @param format	日期格式
     * @return
     */
    public static String formatDate(Date date, String format){
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
    	return sdf.format(date);	
    }

	/**
	 * 格式化日期
	 * 
	 * @param dateStr 	输入的日期字符串(只支持yyyy-MM-dd HH:mm:ss 或 yyyy/MM/dd HH:mm:ss)
	 * @param format 	输出日期格式
	 * @return String 格式化后的日期,如果不能格式化则输出原日期字符串
	 */	
	public static String formatDate(String dateStr, String format) {
		String resultStr = dateStr;
		String inputFormat = "yyyy-MM-dd HH:mm:ss";
		if (dateStr == null) {
			return "";
		}
		inputFormat = matches(dateStr);
		resultStr = formatDate(dateStr, inputFormat, format);
		return resultStr;
	}

	/**
	 * 格式化日期
	 * 
	 * @param dateStr  		输入的日期字符串
	 * @param inputFormat	输入日期格式
	 * @param format		输出日期格式
	 * @return String 格式化后的日期,如果不能格式化则输出原日期字符串
	 */
	public static String formatDate(String dateStr, String inputFormat, String format) {
		String resultStr = dateStr;
		try {
			Date date = new SimpleDateFormat(inputFormat).parse(dateStr);
			resultStr = formatDate(date, format);
		} catch (ParseException e) {
		}
		return resultStr;
	}
	
	/**
	 * 获取字符串格式日期的时间戳
	 * 
	 * @param dateStr  只支持格式:yyyy-MM-dd, yyyy-MM-dd HH:mm:ss, yyyy/MM/dd, yyyy/MM/dd HH:mm:ss
	 * @return 时间戳
	 */
	public static long getTimeInMillis(String dateStr){
		String formatStr = matches(dateStr);
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		long time = 0;
		try {
			time = sdf.parse(dateStr).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	/**
	 * 按格式转换输入字符串为Date对象类型
	 * 
	 * @param dateStr	被格式化的字符串
	 * @param format	字符串的格式
	 * @return	如果输入格式错误, 返回null
	 */
	public static Date formatString(String dateStr, String format){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}
    
	/**
	 * 当前时间增加年数,注意遇到2月29日情况，系统会自动延后或者减少一天。
	 * 
	 * @param years 正值时时间延后，负值时时间提前。
	 * @return Date
	 */
	public static Date addYears(int years) {
		return new Date(addYears2Time(years));
	}

	/**
	 * 返回当前日期 + year的日期的时间戳(毫秒)
	 * 
	 * @param years 正值时时间延后，负值时时间提前
	 * @return
	 */
	public static long addYears2Time(int years) {
		Calendar a = Calendar.getInstance();
		a.setTime(new Date());
		a.add(Calendar.YEAR, years);
		return a.getTimeInMillis();
	}
     
    /**
     * 输入时间增加年数,注意遇到2月29日情况，系统会自动延后或者减少一天。
     * 
     * @param date 
     * @param years 正值时时间延后，负值时时间提前。
     * @return Date
     */
    public static Date addYears(Date date, int years){
        return new Date(addYears2Time(date, years));
    }
	
	/**
	 * 返回date + year的日期的时间戳(毫秒)
	 * 
	 * @param date
	 * @param years 正值时时间延后，负值时时间提前
	 * @return
	 */
	public static long addYears2Time(Date date, int years) {
		Calendar a = Calendar.getInstance();
		a.setTime(date);
		a.add(Calendar.YEAR, years);
		return a.getTimeInMillis();
	}
    
	/**
     * 当前时间增加月数
     * 
     * @param date 
     * @param months 正值时时间延后，负值时时间提前
     * @return Date
     */
    public static Date addMonths(int months){
        return new Date(addMonths2Time(months));
    }
	
	/**
	 * 返回当前日期 + month的日期的时间戳(毫秒)
	 * 
	 * @param months 正值时时间延后，负值时时间提前
	 * @return
	 */
	public static long addMonths2Time(int months) {
		Calendar a = Calendar.getInstance();
		a.setTime(new Date());
		a.add(Calendar.MONTH, months);
		return a.getTimeInMillis();
	}

	/**
     * 输入时间增加月数
     * 
     * @param date 
     * @param months 正值时时间延后，负值时时间提前
     * @return Date
     */
    public static Date addMonths(Date date, int months){
        return new Date(addMonths2Time(date, months));
    }
	
	/**
	 * 返回date + month的日期的时间戳(毫秒)
	 * 
	 * @param date
	 * @param months 正值时时间延后，负值时时间提前
	 * @return
	 */
	public static long addMonths2Time(Date date, int months) {
		Calendar a = Calendar.getInstance();
		a.setTime(date);
		a.add(Calendar.MONTH, months);
		return a.getTimeInMillis();
	}
	
	/**
	 * 获取今天0点0分的时间戳
	 * 
	 * @return
	 */
	public static long getTodayTime() {
		long current = System.currentTimeMillis();//当前时间毫秒数
		return current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
	}
	
	/**
	 * 获取所需要添加year,month后的0点0分的时间戳
	 * 
	 * @param years 正值时时间延后，负值时时间提前
	 * @param months 正值时时间延后，负值时时间提前
	 * @return
	 */
	public static long getTimeByToday(int years, int months) {
		long current = addMonths2Time(years*12 + months);
		return current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
	}
	
	/**
	 * 获取多少天前的时间戳
	 * 
	 * @param days    天数
	 * @param hour    时 0-23
	 * @param minute  分 0-59
	 * @param second  秒 0-59
	 * @return 时间戳
	 */
	public static long getTimeInMillis(int days, int hour, int minute, int second ){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH);
	    int date = calendar.get(Calendar.DAY_OF_MONTH) - days;   
	    calendar.set(year, month, date, hour, minute, second);   
		long time = calendar.getTimeInMillis();  
		time = time/1000L*1000L;   //最后3位毫秒数归零
		return time;
	}
	
	/**
	 * 获取本周星期几的0点时间戳
	 * 
	 * @param week	如：Calendar.MONDAY 需要周几就传周几
	 * @return
	 */
	public static long getWeekTime(int week){
		return getWeekTime(week, 0, 0, 0);
	}
	
	/**
	 * 获取本周星期几的指定时间戳
	 * 
	 * @param week		如：Calendar.MONDAY 需要周几就传周几
	 * @param hour  	时 0-23
	 * @param minute	分 0-59
	 * @param second	秒 0-59
	 * @return
	 */
	public static long getWeekTime(int week, int hour, int minute, int second){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int date = calendar.get(Calendar.DAY_OF_MONTH);  
		calendar.set(year, month, date, hour, minute, second); 
		calendar.set(Calendar.DAY_OF_WEEK, week);
		long time = calendar.getTimeInMillis();  
		time = time/1000L*1000L;   //最后3位毫秒数归零
		return time;
	}
	
	/**
	 * 获取当前年
	 * 
	 * @return
	 */
	public static int getYear(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 获取当前月
	 * 
	 * @return
	 */
	public static int getMonth(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH)+1;
	}
	
	/**
	 * 获取当前日
	 * 
	 * @return
	 */
	public static int getDayOfMonth(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取当前小时
	 * 
	 * @return
	 */
	public static int getHour(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 获取当前分钟
	 * 
	 * @return
	 */
	public static int getMinute(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MINUTE);
	}
	
	/**
	 * 获取当前秒
	 * 
	 * @return
	 */
	public static int getSecond(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.SECOND);
	}
	
	/**
	 * 获取所需要添加year,month后的日期
	 * 
	 * @param years 正值时时间延后，负值时时间提前
	 * @param months 正值时时间延后，负值时时间提前
	 * @return
	 */
	public static Date getDateByToday(int years, int months) {
		return addMonths(years*12 + months);
	}
	
	/**
	 * 获取开始时间和结束时间的的天数
	 * @param begin	必须是yyyy-MM-dd的格式
	 * @param end	必须是yyyy-MM-dd的格式
	 * @return
	 */
   	public static long getBetweenDays(String begin, String end) {
   		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
   		try {
   			Date dateBegin=sdf.parse(begin);
   	   		Date dateEnd=sdf.parse(end);
   	   		return getBetweenDays(dateBegin, dateEnd);	
		} catch (Exception e) {
			return 0;
		}
   	}
   	
	/**
	 * 获取开始时间和结束时间的的天数
	 * 
	 * @param begin	Date 开始时间
	 * @param end	Date 结束时间
	 * @return
	 */
   	public static long getBetweenDays(Date begin, Date end) {
   		return getBetweenDays(begin, end , false);
   	}
   	
	/**
	 * 获取开始时间和结束时间的的天数
	 * 
	 * @param begin	Date 开始时间
	 * @param end	Date 结束时间
	 * @return
	 */
   	public static long getBetweenDays(long begin, long end) {
   		return getBetweenDays(begin, end , false);
   	}
   	
   	/**
   	 * 计算两个日期之间的时间间隔(d1-d2)，可选择是否计算工作日
   	 * 
   	 * @param begin
   	 * @param end
   	 * @param onlyWorkDay 是否只计算工作日
   	 * @return 
   	 */
   	public static long getBetweenDays(Date begin,Date end,boolean onlyWorkDay) {
   		return getBetweenDays(begin.getTime(), end.getTime(), false);
   	}
   	
   	/**
   	 * 计算两个日期之间的时间间隔(d1-d2)，可选择是否计算工作日
   	 * 
   	 * @param begin
   	 * @param end
   	 * @param onlyWorkDay 是否只计算工作日
   	 * @return 
   	 */
   	public static long getBetweenDays(long begin,long end,boolean onlyWorkDay) {
		if (!onlyWorkDay) {
			return (begin - end) / ONE_DAY + 1;
		} else {
			long days = 0;
			Calendar calendar = Calendar.getInstance();
			try {
				days = (begin - end) / ONE_DAY;
				for (calendar.setTimeInMillis(begin); calendar.getTimeInMillis() < end; calendar.add(Calendar.DAY_OF_YEAR, -1)) {
					int week = calendar.get(Calendar.DAY_OF_WEEK);
					if (week == Calendar.SUNDAY || week == Calendar.SATURDAY) {
						days--;
					}
				}
				if (days < 0) {
					days = 0;
				}
			} catch (Exception e) {
			}
			return days;
		}
   	}
   	
   	/**
   	 * 判断是否是工作日
   	 * 
   	 * @param date
   	 * @return
   	 */
   	public static boolean isWorkDay(Date date){
   		return isWorkDay(date.getTime());
   	}
   	
   	/**
   	 * 判断是否是工作日
   	 * 
   	 * @param date
   	 * @return
   	 */
   	public static boolean isWorkDay(long date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		if (week == Calendar.SUNDAY || week == Calendar.SATURDAY) {
			return false;
		} else {
			return true;
		}
   	}
   	
   	/**
   	 * 计算两个时间 按什么单位区分的间隔
   	 * 
   	 * @param begin
   	 * @param end
   	 * @param unit	DateUtil.ONEDAT,ONEHOUR,ONEMINUTE,ONESECOND
   	 * @return
   	 */
   	public static long getBetweenUnits(Date begin, Date end, long unit) {
   		return getBetweenUnits(begin.getTime(), end.getTime(), unit);
   	}
   	
   	/**
   	 * 计算两个时间 按什么单位区分的间隔
   	 * 
   	 * @param begin
   	 * @param end
   	 * @param unit	DateUtil.ONEDAT,ONEHOUR,ONEMINUTE,ONESECOND
   	 * @return
   	 */
   	public static long getBetweenUnits(long begin, long end, long unit) {
		try {
			return (begin - end) / unit;
		} catch (Exception e) {
			return 0;
		}
   	}

	/**
	 * 获取返回的中文时间(中文)
	 * 
	 * @param sub		减数
	 * @param min		被减数
	 * @param length	长度(1代表到年, 2代表到月, 3代表到日, 4代表到时, 5代表到分, 6代表到秒, 7代表到毫秒)
	 * @return
	 */
	public static String getSubtractTimeStr(Date sub, Date min, int length) {
		return getSubtractTimeStr(sub, min, length, 1, "");
	}

	/**
	 * 获取返回的中文时间(中文)
	 * 
	 * @param sub		减数
	 * @param min		被减数
	 * @param length	长度(1代表到年, 2代表到月, 3代表到日, 4代表到时, 5代表到分, 6代表到秒, 7代表到毫秒)
	 * @return
	 */
	public static String getSubtractTimeStr(long sub, long min, int length) {
		return getSubtractTimeStr(sub, min, length, 1, "");
	}
	
	/**
	 * 获取返回的中文时间(英文)
	 * 
	 * @param sub		减数
	 * @param min		被减数
	 * @param length	长度(1代表到年, 2代表到月, 3代表到日, 4代表到时, 5代表到分, 6代表到秒, 7代表到毫秒)
	 * @return
	 */
	public static String getSubtractTimeEnStr(Date sub, Date min, int length) {
		return getSubtractTimeStr(sub, min, length, 2, "");
	}
	
	/**
	 * 获取返回的中文时间(英文)
	 * 
	 * @param sub		减数
	 * @param min		被减数
	 * @param length	长度(1代表到年, 2代表到月, 3代表到日, 4代表到时, 5代表到分, 6代表到秒, 7代表到毫秒)
	 * @return
	 */
	public static String getSubtractTimeEnStr(long sub, long min, int length) {
		return getSubtractTimeStr(sub, min, length, 2, "");
	}
	
	/**
	 * 返回自定义间隔的2个时间相减得时间字符串
	 * 
	 * @param sub		减数
	 * @param min		被减数
	 * @param length	长度(1代表到年, 2代表到月, 3代表到日, 4代表到时, 5代表到分, 6代表到秒, 7代表到毫秒)
	 * @param sep		间隔符
	 * @return
	 */
	public static String getSubtractTimeStr(Date sub, Date min, int length, String sep) {
		return getSubtractTimeStr(sub, min, length, 0, sep);
	}

	/**
	 * 返回自定义间隔的2个时间相减得时间字符串
	 * 
	 * @param sub		减数
	 * @param min		被减数
	 * @param length	长度(1代表到年, 2代表到月, 3代表到日, 4代表到时, 5代表到分, 6代表到秒, 7代表到毫秒)
	 * @param sep		间隔符
	 * @return
	 */
	public static String getSubtractTimeStr(long sub, long min, int length, String sep) {
		return getSubtractTimeStr(sub, min, length, 0, sep);
	}
	
	/**
	 * 按自定义语言返回相减得时间字符串
	 * 
	 * @param sub		减数
	 * @param min		被减数
	 * @param length	长度(1代表到年, 2代表到月, 3代表到日, 4代表到时, 5代表到分, 6代表到秒, 7代表到毫秒)
	 * @param language	0自定义 1中文 2英文
	 * @param sep		间隔符
	 * @return
	 */
	public static String getSubtractTimeStr(Date sub, Date min, int length, int language, String sep) {
		return getSubtractTimeStr(sub.getTime(), min.getTime(), length, language, sep);
	}

	/**
	 * 按自定义语言返回相减得时间字符串
	 * 
	 * @param sub		减数
	 * @param min		被减数
	 * @param length	长度(1代表到年, 2代表到月, 3代表到日, 4代表到时, 5代表到分, 6代表到秒, 7代表到毫秒)
	 * @param language	0自定义 1中文 2英文
	 * @param sep		间隔符
	 * @return
	 */
	public static String getSubtractTimeStr(long sub, long min, int length, int language, String sep) {
		if (sub - min < 0) {
			throw new RuntimeException("min must bigger than sub!");
		}
		Calendar subC = Calendar.getInstance();
		Calendar minC = Calendar.getInstance();
		subC.setTimeInMillis(sub);
		minC.setTimeInMillis(min);
		int startYear = minC.get(Calendar.YEAR);
		int startMonth = minC.get(Calendar.MONTH);
		int year = subC.get(Calendar.YEAR) - minC.get(Calendar.YEAR);
		int month = subC.get(Calendar.MONTH) - minC.get(Calendar.MONTH);
		int day = subC.get(Calendar.DAY_OF_MONTH) - minC.get(Calendar.DAY_OF_MONTH);
		int hour = subC.get(Calendar.HOUR) - minC.get(Calendar.HOUR);
		int minute = subC.get(Calendar.MINUTE) - minC.get(Calendar.MINUTE);
		int second = subC.get(Calendar.SECOND) - minC.get(Calendar.SECOND);
		int millSecond = subC.get(Calendar.MILLISECOND) - minC.get(Calendar.MILLISECOND);
		if (millSecond < 0) {
			millSecond += 1000;
			second -= 1;
		}
		if (second < 0) {
			second += 60;
			minute -= 1;
		}
		if (minute < 0) {
			minute += 60;
			hour -= 1;
		}
		if (hour < 0) {
			hour += 24;
			day -= 1;
		}
		if (day < 0) {
			day += getDaysOfYearMonth(startYear, startMonth);
			month -= 1;
		}
		if (month < 0) {
			month += 12;
			year -= 1;
		}
		if (year < 0) {
			throw new RuntimeException("error year!");
		}
		return toStr(year, month, day, hour, minute, second, millSecond, length, language, sep);
	}
	
	/**
	 * 根据长度 返回中文字符串的日期
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @param millSecond
	 * @param length	长度(1代表到年, 2代表到月, 3代表到日, 4代表到时, 5代表到分, 6代表到秒, 7代表到毫秒)
	 * @param language  0自定义 1中文 2英文
	 * @param sep		自定义的间隔字符
	 * @return
	 */
	private static String toStr(int year, int month, int day, int hour, int minute, int second, int millSecond, int length, int language, String sep) {
		String yearS = year + (language == 1 ? "年" : language == 2 ? "year " : sep);
		String monthS = month + (language == 1 ? "月" : language == 2 ? "month " : sep);
		String dayS = day + (language == 1 ? "日" : language == 2 ? "day " : sep);
		String hourS = hour + (language == 1 ? "时" : language == 2 ? "hour " : sep);
		String minuteS = minute + (language == 1 ? "分" : language == 2 ? "minute " : sep);
		String secondS = second + (language == 1 ? "秒" : language == 2 ? "second " : sep);
		String millSecondS = millSecond + (language == 1 ? "毫秒" : language == 2 ? "millSecond " : sep);
		StringBuilder sb = new StringBuilder();
		switch(length) {
		case 1 :
			sb.append(yearS);
			break;
		case 2:
			sb.append(year > 0 ? yearS + monthS : monthS);
			break;
		case 3:
			sb.append(year == 0 ? "" : yearS);
			sb.append(month == 0 ? "" : monthS);
			sb.append(dayS);
			break;
		case 4:
			sb.append(year == 0 ? "" : yearS);
			sb.append(month == 0 ? "" : monthS);
			sb.append(day == 0 ? "" : dayS);
			sb.append(hourS);
			break;
		case 5:
			sb.append(year == 0 ? "" : yearS);
			sb.append(month == 0 ? "" : monthS);
			sb.append(day == 0 ? "" : dayS);
			sb.append(hour == 0 ? "" : hourS);
			sb.append(minuteS);
			break;
		case 6:
			sb.append(year == 0 ? "" : yearS);
			sb.append(month == 0 ? "" : monthS);
			sb.append(day == 0 ? "" : dayS);
			sb.append(hour == 0 ? "" : hourS);
			sb.append(minute == 0 ? "" : minuteS);
			sb.append(secondS);
			break;
		default:
			sb.append(year == 0 ? "" : yearS);
			sb.append(month == 0 ? "" : monthS);
			sb.append(day == 0 ? "" : dayS);
			sb.append(hour == 0 ? "" : hourS);
			sb.append(minute == 0 ? "" : minuteS);
			sb.append(second == 0 ? "" : secondS);
			sb.append(millSecondS);
			break;
		}
		return sb.toString();
		
	}

	private static Map<Integer, Integer> dayOfMonth = new HashMap<Integer, Integer>();
	static {
		dayOfMonth.put(0, 31);	//对应12月的31天
		dayOfMonth.put(1, 31);
		dayOfMonth.put(2, 28);	//2月 分闰年(29) 和  常规年(28)
		dayOfMonth.put(3, 31);
		dayOfMonth.put(4, 30);
		dayOfMonth.put(5, 31);
		dayOfMonth.put(6, 30);
		dayOfMonth.put(7, 31);
		dayOfMonth.put(8, 31);
		dayOfMonth.put(9, 30);
		dayOfMonth.put(10, 31);
		dayOfMonth.put(11, 30);
		dayOfMonth.put(12, 31);
	}
	
	/**
	 * 获取当前月份在今年 有多少天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysOfYearMonth(int year, int month) {
		if (month != 2) {
			return dayOfMonth.get(month);
		}
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {	//闰年
			return 29;
		} else {
			return dayOfMonth.get(month);
		}
	}
	
	/**
	 * 获取当前年有多少天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysOfYear(int year) {
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {	//闰年
			return 366;
		} else {
			return 365;
		}
	}
	
//	public static void main(String[] args) {
//		Date sub = new Date();
//		Date min = new Date();
//		Calendar c = Calendar.getInstance();
//		c.setTime(sub);
//		c.add(Calendar.YEAR, 1);
//		c.add(Calendar.MONTH, 2);
//		System.out.println(getSubtractTimeStr(c.getTime(), min, 2, 1, "-"));
//	}
}
