package com.wande.ssy.utils;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证数据工具类
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年2月8日 下午1:51:10
 */
public class ValidateUtil {
	
	///////////////////////////// 判断是否为整数
	/**
	 * 判断是否为整数
	 * 
	 * @param str	传入的字符串
	 * @return 		是整数返回true,否则返回false
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * 判断是否为浮点数，包括double和float
	 * 
	 * @param str	传入的字符串
	 * @return 		是浮点数返回true,否则返回false
	 */
	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?\\d+\\.\\d+$");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * 判断是不是合法字符 c 要判断的字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isLetter(String str) {
		if (str == null || str.length() < 0) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\w\\.-_]*");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * 判断是不是合法的手机号码
	 * 
	 * @param phone
	 * @return boolean	是为true,否则为false
	 */
	public static boolean isPhone(String phone) {
		try {
			String regex = "^(((13[0-9]{1})|(15[0-9]{1})|(17[6-8]{1})|(14[5-7]{1})|(18[0-9]{1}))+\\d{8})$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(phone);
			return matcher.matches();
		} catch (RuntimeException e) {
			return false;
		}
	}
	
	/**
	 * 判断输入的字符串是否符合邮箱Email样式.
	 * 
	 * @param str	传入的字符串
	 * @return 		是Email样式返回true,否则返回false
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.length() < 1 || email.length() > 256) {
			return false;
		}
		Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
//		Pattern pattern = Pattern.compile("^([\\w\\.\\-]+)\\@(\\w+)(\\.([\\w^\\_]+)){1,2}$");
		return pattern.matcher(email).matches();
	}
	
	/**
	 * 判断输入的字符串是否为纯汉字
	 * 
	 * @param str	传入的字符窜
	 * @return 		如果是纯汉字返回true,否则返回false
	 */
	public static boolean isChinese(String str) {
		Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * 功能描述：判断是否为质数
	 * 
	 * @param x
	 * @return
	 */
	public static boolean isPrime(int x) {
		if (x <= 7) {
			if (x == 2 || x == 3 || x == 5 || x == 7)
				return true;
		}
		int c = 7;
		if (x % 2 == 0)
			return false;
		if (x % 3 == 0)
			return false;
		if (x % 5 == 0)
			return false;
		int end = (int) Math.sqrt(x);
		while (c <= end) {
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 6;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 6;
		}
		return true;
	}
}
