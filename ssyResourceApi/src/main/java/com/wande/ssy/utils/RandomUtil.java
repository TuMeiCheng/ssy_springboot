package com.wande.ssy.utils;

import java.util.Random;

/**
 * 获取随机数值或字符串工具
 * 
 * @author vwFisher(422578659@qq.com)
 * 2016年12月14日上午9:11:19
 */
public class RandomUtil {
	
	private static String numChar = "0123456789";	//数字
	
	private static String upperWord = "QWERTYUIOPASDFGHJKLZXCVBNM";	//全大写
	
	private static String lowerWord = "qwertyuiopasdfghjklzxcvbnm";	//全小写
	
	private static String otherChar = "`~!@#$%^&*()_+-=|[]{}';:.,<>?\\/\"\\";	//其他字符, 待测试
	
	/**
	 * 创建随机数,默认范围在0-9a-zA-Z中
	 * @param num	个数
	 * @return
	 */
	public static String createCode(int num){
		return createCode(num, "1,2,3");
	}
	
	/**
	 * 创建随机数
	 * @param num	个数
	 * @param temp	随机范围
	 * @return
	 */
	public static String createCustomCode(int num, String temp) {
		String vcode = "";
		for (int i = 0; i < num; i++) {
			int index = (int) (Math.random() * (temp.length() - 1));
			vcode = vcode + temp.charAt(index);
		}
		return vcode;
	}
	
	/**
	 * 创建随机数
	 * @param num	个数
	 * @param type	"1"代表纯数字 或者  "1,2"代表数字加26个大写字母
	 * 		类型可选 	1.纯数字, 2.纯大写26个字母, 3.纯小写26个字母, 4.其他字符
	 * 
	 * @return
	 */
	public static String createCode(int num, String type) {
		StringBuilder s = new StringBuilder();
		if (type.indexOf("1") != -1) 
			s.append(numChar);
		if (type.indexOf("2") != -1) 
			s.append(upperWord);
		if (type.indexOf("3") != -1) 
			s.append(lowerWord);
		if (type.indexOf("4") != -1) 
			s.append(otherChar);
		String temp = s.toString();
		String vcode = "";
		for (int i = 0; i < num; i++) {
			int index = (int) (Math.random() * (temp.length() - 1));
			vcode = vcode + temp.charAt(index);
		}
		return vcode;
	}

	/**
	 * 在指定范围中获取随机数
	 * 
	 * @param start	开始范围
	 * @param end	结束范围
	 * @return
	 */
	public static int getNum(int start, int end) {
		return new Random().nextInt(end - start) + start;
	}
	
	public static void main(String[] args){
		System.out.println(createCustomCode(6, "123456"));
	}
}
