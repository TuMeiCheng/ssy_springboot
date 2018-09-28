package com.wande.ssy.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年2月8日 下午1:51:10
 */
public final class StringUtil {
	/**UTF-8编码常量*/
	public static final String ENC_UTF8 = "UTF-8";
	/**GBK编码常量*/
	public static final String ENC_GBK = "GBK";
	/**GBK的Charset*/
    public static final Charset GBK = Charset.forName("GBK");
    /**UTF-8的Charset*/
    public static final Charset UTF_8 = Charset.forName("UTF-8");

	/**精确到秒的日期时间格式化的格式字符串*/
	public static final String FMT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 转换&#123;这种编码为正常字符<br/>
	 * 有些手机会将中文转换成&#123;这种编码,这个函数主要用来转换成正常字符.
	 * 
	 * @param str
	 * @return String
	 */
	public static String decodeNetUnicode(String str) {
		if (str == null)
			return null;
		String pStr = "&#(\\d+);";
		Pattern p = Pattern.compile(pStr);
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String mcStr = m.group(1);
			int charValue = StringUtil.convertInt(mcStr, -1);
			String s = charValue > 0 ? (char) charValue + "" : "";
			m.appendReplacement(sb, Matcher.quoteReplacement(s));
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	
	/**
	 * 过滤SQL字符串,防止SQL 注入
	 * 另一种避免方式 是使用PreparedStatement
	 * 
	 * @param sql
	 * @return String
	 */
	public static String encodeSQL(String sql){
		if (sql == null) {
			return "";
		}
		// 不用正则表达式替换，直接通过循环，节省cpu时间
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < sql.length(); ++i) {
			char c = sql.charAt(i);
			switch (c) {
			case '\\':
				sb.append("\\\\");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\'':
				sb.append("\\\'");
				break;
			case '\"':
				sb.append("\\\"");
				break;
			case '\u200B':// ZERO WIDTH SPACE
			case '\uFEFF':// ZERO WIDTH NO-BREAK SPACE
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
    /**
     * 获取字符型参数，若输入字符串为null，则返回设定的默认值
     * 
     * @param str 输入字符串
     * @param defaults 默认值 
     * @return 字符串参数
     */
    public static String convertString(String str, String defaults){
		if (str == null) {
			return defaults;
		} else {
			return str;
		}
    }
        
    /**
     * 获取int参数，若输入字符串为null或不能转为int，则返回设定的默认值
     * 
     * @param str 输入字符串
     * @param defaults 默认值
     * @return int参数
     */
    public static int convertInt(String str, int defaults){
		if (str == null) {
			return defaults;
		}
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return defaults;
		}
    }
    
    
    /**
     * 获取long型参数，若输入字符串为null或不能转为long，则返回设定的默认值
     * @param str 输入字符串
     * @param defaults 默认值
     * @return long参数
     */
    public static long convertLong(String str, long defaults) {
		if (str == null) {
			return defaults;
		}
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return defaults;
		}
    }
    
    
    /**
     * 获取double型参数，若输入字符串为null或不能转为double，则返回设定的默认值
     * @param str 输入字符串
     * @param defaults 默认值
     * @return double型参数
     */
    public static double convertDouble(String str, double defaults) {
		if (str == null) {
			return defaults;
		}
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			return defaults;
		}
    }
    
    /**
     * 获取short型参数，若输入字符串为null或不能转为short，则返回设定的默认值
     * @param str 输入字符串
     * @param defaults 默认值
     * @return short型参数
     */
    public static short convertShort(String str, short defaults){
		if (str == null) {
			return defaults;
		}
		try {
			return Short.parseShort(str);
		} catch (Exception e) {
			return defaults;
		}
    }
    
    /**
     * 获取float型参数，若输入字符串为null或不能转为float，则返回设定的默认值
     * @param str 输入字符串
     * @param defaults 默认值
     * @return float型参数
     */
    public static float convertFloat(String str, float defaults){
		if (str == null) {
			return defaults;
		}
		try {
			return Float.parseFloat(str);
		} catch (Exception e) {
			return defaults;
		}
    }
    
    /**
     * 获取boolean型参数，若输入字符串为null或不能转为boolean，则返回设定的默认值
     * @param str 输入字符串
     * @param defaults 默认值
     * @return boolean型参数
     */
    public static boolean convertBoolean(String str, boolean defaults){
		if (str == null) {
			return defaults;
		}
		try {
			return Boolean.parseBoolean(str);
		} catch (Exception e) {
			return defaults;
		}
    }
    
    /**
     * 分割字符串
	 * 
	 * 例如
	 * split("1-2-3", "-") -> {"1","2","3"}
	 * split("-1--2-", "-") -> {"","1","","2",""} 
	 * split("123", "") -> {"123"}
	 * split("1-2---3----4", "--") -> {"1-2","-3","","4"}
     * 
     * @param line			原始字符串
     * @param seperator		分隔符
     * @return				分割结果
     */
	public static String[] split(String line, String seperator) {
		if (line == null || seperator == null || seperator.length() == 0) {
			return new String[0];
		}
		ArrayList<String> list = new ArrayList<String>();
		int pos1 = 0;
		int pos2;
		for (;;) {
			pos2 = line.indexOf(seperator, pos1);
			if (pos2 < 0) {
				list.add(line.substring(pos1));
				break;
			}
			list.add(line.substring(pos1, pos2));
			pos1 = pos2 + seperator.length();
		}
		// 去掉末尾的空串，和String.split行为保持一致
		for (int i = list.size() - 1; i >= 0 && list.get(i).length() == 0; --i) {
			list.remove(i);
		}
		return list.toArray(new String[0]);
	}
    
    
	/**
	 * 分割字符串，并转换为int数组
	 * 
	 * @param line		原始字符串
	 * @param seperator	分隔符
	 * @param def		默认值
	 * @return 分割结果
	 */
	public static int[] splitInt(String line, String seperator, int def) {
		String[] ss = split(line, seperator);
		int[] r = new int[ss.length];
		for (int i = 0; i < r.length; ++i) {
			r[i] = convertInt(ss[i], def);
		}
		return r;
	}
	
	
	/**
	 * 分割字符串，并转换为long数组
	 * 
	 * @param line		原始字符串
	 * @param seperator	分隔符
	 * @param def		默认值
	 * @return 分割结果
	 */
	public static long[] splitLong(String line, String seperator, long def) {
		String[] ss = split(line, seperator);
		long[] r = new long[ss.length];
		for (int i = 0; i < r.length; ++i) {
			r[i] = convertLong(ss[i], def);
		}
		return r;
	}
	
	/**
	 * 根据regex对字符串s进行分割(当遇到regex,将该regex的位置进行左右分割)
	 * 
	 * 例如
	 * splitIncludeRegex("a--bb-cc---d", "-") -> a,-,,-,bb,-,cc,-,,-,,-,d,
	 * 
	 * @param s		被分割的字符串
	 * @param regex	分割正则匹配值
	 * @return
	 */
	public static String[] splitIncludeRegex(String s, String regex){
		int regexLength;
		int stringLength = s.length();
		if (regex == null || (regexLength = regex.length()) == 0){
			return new String[] {s};
		}

		int count, start, end;
		//计算count个数, 初始化String[]
		count = 0;
		start = 0;
		while((end = s.indexOf(regex, start)) != -1){
			count+=2;
			start = end + regexLength;
		}
		count++;
		String[] result = new String[count];

		//对String[]进行赋值
		count = 0;
		start = 0;
		while((end = s.indexOf(regex, start)) != -1){
			result[count] = (s.substring(start, end));
			count++;
			result[count] = regex;
			count++;
			start = end + regexLength;
		}
		end = stringLength;
		result[count] = s.substring(start, end);

		return (result);
	}
    
    /**
     * 字符串全量替换
     * 
     * @param s			原始字符串
     * @param src		要替换的字符串
     * @param dest		替换目标
     * @return			结果
     */
    public static String replaceAll(String s, String src, String dest){
    	if(s == null || src == null || dest == null || src.length() == 0)
    		return s;
    	int pos = s.indexOf(src);			// 查找第一个替换的位置
    	if(pos < 0)
    		return s;
    	int capacity = dest.length() > src.length() ? s.length() * 2: s.length();
    	StringBuilder sb = new StringBuilder(capacity);
    	int writen = 0;
    	for(; pos >= 0; ){
    		sb.append(s, writen, pos);		// append 原字符串不需替换部分
    		sb.append(dest);				// append 新字符串
    		writen = pos + src.length();	// 忽略原字符串需要替换部分
    		pos = s.indexOf(src, writen);	// 查找下一个替换位置
    	}
    	sb.append(s, writen, s.length());	// 替换剩下的原字符串
    	return sb.toString();
    }
    
    /**
     * 只替换第一个字符串
     * 
     * @param s			原始字符串
     * @param src		要替换的字符串
     * @param dest		替换目标
     * @return			结果
     */
	public static String replaceFirst(String s, String src, String dest){
		if(s == null || src == null || dest == null || src.length() == 0)
			return s;
		int pos = s.indexOf(src);
		if(pos < 0){
			return s;
		}
		StringBuilder sb = new StringBuilder(s.length() - src.length() + dest.length());
		
		sb.append(s, 0, pos);
		sb.append(dest);
		sb.append(s, pos + src.length(), s.length());
		return sb.toString();
	}
	
    /**
     * 删除指定的全部字符串
     * 
     * @param s 原字符串
     * @param src 需要删除的字符串
     * @return
     */
    public static String removeAll(String s, String src){
    	return replaceAll(s, src, "");
    }
    
    /**
     * 删除指定的第一个字符串
     * 
     * @param s 原字符串
     * @param src 需要删除的字符串
     * @return
     */
    public static String removeFirst(String s, String src){
    	return replaceFirst(s, src, "");
    }
	
    /**
     * 将字符串分割后,每段首字母大写后再拼接
     *
     * @param line 原字符串
     * @param seperator 分隔符
     * @return String
     */
    public static String splitToUpperCase(String line, String seperator){
    	String str = "";
    	String[] array = split(line, seperator);
    	for (String s : array) {
			str += toUpperCase(s, 0);
		}
		return str;
    }
    
    /**
     * 将字符串指定下标的字符转大写
     * 
     * @param str
     * @param index
     * @return String
     */
    public static String toUpperCase(String str, int index){
		char[] cs = str.toCharArray();
    	char c = cs[index];
    	if(c>=97 && c<=122){
    		cs[index] -= 32;
    	}
		return String.valueOf(cs);
    }
    
    /**
     * 将字符串指定下标的字符转小写
     * 
     * @param str
     * @param index
     * @return String
     */
    public static String toLowerCase(String str, int index){
    	char[] cs = str.toCharArray();
    	char c = cs[index];
    	if(c>=35 && c<=90){
    		cs[index] += 32;
    	}
    	return String.valueOf(cs);
    }
    
	/**
	 * 判断字符串为空,true= [null or s.trim().isEmpty()]
	 * @param s
	 * 
	 * @return true=空
	 */
	public static boolean isEmpty(String s) {
		if (s == null)
			return true;
		return s.trim().isEmpty();
	}
	
	/**
	 * 判断字符串不为空
	 * @param s
	 * 
	 * @return true=不为空
	 */
	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}
	
	/**
	 * 处理null数据为空字符串
	 * 
	 * @param s
	 * @return	如果为null返回"", 否则返回原字符串
	 */
	public static String dealNull(String s) {
		return s == null ? "" : s; 
	}

	/**
	 * 获取字符串的UTF-8编码字节数组
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] getUTF8Bytes(String s) {
		if (s != null && s.length() >= 0) {
			return s.getBytes(UTF_8);
		}
		return null;
	}

	/**
	 * 获取字符串的GBK编码字节数组
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] getGBKBytes(String s) {
		if (s != null && s.length() >= 0) {
			return s.getBytes(GBK);
		}
		return null;
	}
	
	/**
	 * 获取字节数组的UTF-8编码字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String getUTF8String(byte[] b) {
		if (b != null) {
			return new String(b, UTF_8);
		}
		return null;
	}

	/**
	 * 获取字节数组的GBK编码字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String getGBKString(byte[] b) {
		if (b != null) {
			return new String(b, GBK);
		}
		return null;
	}
	
	/**
	 * 对字符串以 GBK编码方式进行URLEncode
	 * 
	 * @param s
	 * @return
	 */
	public static String URLEncodeGBK(String s) {
		if (s != null && s.length() > 0) {
			try {
				return URLEncoder.encode(s, ENC_GBK);
			} catch (UnsupportedEncodingException e) {
			}
		}
		return s;
	}

	/**
	 * 对字符串以 UTF-8编码方式进行URLEncode
	 * 
	 * @param s
	 * @return
	 */
	public static String URLEncodeUTF8(String s) {
		if (s != null && s.length() > 0) {
			try {
				return URLEncoder.encode(s, ENC_UTF8);
			} catch (UnsupportedEncodingException e) {
			}
		}
		return s;
	}
	
	/**
	 * 对字符串以 GBK编码方式进行URLDecode
	 * 
	 * @param s 
	 * @return
	 */
	public static String URLDecodeGBK(String s) {
		if (s != null && s.length() > 0) {
			try {
				return URLDecoder.decode(s, ENC_GBK);
			} catch (UnsupportedEncodingException e) {
			}
		}
		return s;
	}
	
	/**
	 * 对字符串以 UTF-8编码方式进行URLDecode
	 * 
	 * @param s 
	 * @return
	 */
	public static String URLDecodeUTF8(String s) {
		if (s != null && s.length() > 0) {
			try {
				return URLDecoder.decode(s, ENC_UTF8);
			} catch (UnsupportedEncodingException e) {
			}
		}
		return s;
	}

	///////////////////////////////////////////
	/**
	 * 将字符串填充到所需的长度
	 * 如果字符串长度>length,则不会截断
	 * 如果字符串长度<length,则在前面填充字节c进去
	 * 
	 * 例如:prepad("abc", 5, 'p') -> "ppabc"
	 * 
	 * @param s			被填充的字符串
	 * @param length	所需要的长度
	 * @param c			在字符串前填充的字节
	 * @return
	 */
	public static String prepad(String s, int length, char c) {
		int needed = length - s.length();
		if (needed <= 0) {
			return s;
		}
		char padding[] = new char[needed];
		Arrays.fill(padding, c);
		StringBuffer sb = new StringBuffer(length);
		sb.append(padding);
		sb.append(s);
		return sb.toString();
	}
	
	/**
	 * 将字符串填充到所需的长度
	 * 如果字符串长度>length,则不会截断
	 * 如果字符串长度<length,则在后面填充字节c进去
	 * 
	 * 例如:postpad("abc", 5, 'p') -> "abcpp"
	 * 
	 * @param s			被填充的字符串
	 * @param length	所需要的长度
	 * @param c			在字符串前填充的字节
	 * @return
	 */
	public static String postpad(String s, int length, char c){
		int needed = length - s.length();
		if (needed <= 0){
			return s;
		}
		char padding[] = new char[needed];
		Arrays.fill(padding, c);
		StringBuffer sb = new StringBuffer(length);
		sb.append(s);
		sb.append(padding);
		return sb.toString();
	}
	
	/**
	 * 将字符串填充到所需的长度
	 * 如果字符串长度>length,则不会截断
	 * 如果字符串长度<length,则在前后都填充字节c进去
	 * (长度差=奇数2k+1,后面比前面多插一个,长度差=偶数2k,前后插入相同个数)
	 * 
	 * 例如:midpad("abc", 4, 'p') -> "abcp"
	 * 例如:midpad("abc", 5, 'p') -> "pabcp"
	 * 
	 * 
	 * @param s			被填充的字符串
	 * @param length	所需要的长度
	 * @param c			在字符串前填充的字节
	 * @return
	 */
	public static String midpad(String s, int length, char c){
		int needed = length - s.length();
		if (needed <= 0){
			return s;
		}
		int beginning = needed / 2;
		int end = beginning + needed % 2;
		char prepadding[] = new char[beginning];
		Arrays.fill(prepadding, c);
		char postpadding[] = new char[end];
		Arrays.fill(postpadding, c);
		StringBuffer sb = new StringBuffer(length);
		sb.append(prepadding);
		sb.append(s);
		sb.append(postpadding);
		return sb.toString();
	}
	
	/**
	 * 将字符串数组进行拼接
	 * 
	 * 例如
	 * join({"a","b","c"}) -> "abc"
	 * 
	 * @param array	被拼接的字符串数组
	 * @return
	 */
	public static String join(String[] array){
		return join(array, "");
	}
	
	/**
	 * 将字符串数组用regex进行拼接
	 * 
	 * 例如
	 * join({"a","b","c"}, "-") -> "a-b-c"
	 * 
	 * @param array	被拼接的字符串数组
	 * @param regex	拼接的字符串
	 * @return
	 */
	public static String join(String[] array, String regex){
		int regexLength = regex.length();
		if (array.length == 0) 
			return "";
		if (array.length == 1){
			if (array[0] == null) return "";
			return array[0];
		}
		//计算总长度
		int length = 0;
		for (int i=0; i<array.length; i++){
			if (array[i] != null) 
				length += array[i].length();
			if (i < array.length-1) 
				length += regexLength;
		}
		//进行拼接
		StringBuffer result = new StringBuffer(length);
		for (int i=0; i<array.length; i++){
			if (array[i] != null) 
				result.append(array[i]);
			if (i < array.length-1) 
				result.append(regex);
		}
		return result.toString();
	}
	
/*	public static <T> String join(T... elements) {
		return join(elements, null);
	}
	  
	public static String join(Object[] array, char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}
	  
	public static String join(long[] array, char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}
	  
	public static String join(int[] array, char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}
	  
	public static String join(short[] array, char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}
	  
	public static String join(byte[] array, char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}
	  
	public static String join(char[] array, char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}
	  
	public static String join(float[] array, char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}
	  
	public static String join(double[] array, char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}
	  
	public static String join(Object[] array, char separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder(noOfItems * 16);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}
	  
	public static String join(long[] array, char separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder(noOfItems * 16);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			buf.append(array[i]);
		}
		return buf.toString();
	}
	  
	public static String join(int[] array, char separator, int startIndex, int endIndex) {
		System.out.println("1");
		if (array == null) {
			return null;
		}
		int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder(noOfItems * 16);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			buf.append(array[i]);
		}
		return buf.toString();
	}
	  
	public static String join(byte[] array, char separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder(noOfItems * 16);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			buf.append(array[i]);
		}
		return buf.toString();
	}

	public static String join(short[] array, char separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder(noOfItems * 16);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			buf.append(array[i]);
		}
		return buf.toString();
	}

	public static String join(char[] array, char separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder(noOfItems * 16);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			buf.append(array[i]);
		}
		return buf.toString();
	}

	public static String join(double[] array, char separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder(noOfItems * 16);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			buf.append(array[i]);
		}
		return buf.toString();
	}

	public static String join(float[] array, char separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder(noOfItems * 16);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			buf.append(array[i]);
		}
		return buf.toString();
	}
	  
	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}
	
	public static String join(Object[] array, String separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		if (separator == null) {
			separator = "";
		}

		int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return "";
		}

		StringBuilder buf = new StringBuilder(noOfItems * 16);

		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}*/
	
	/**
	 * 处理如果为null的时候返回"", 不为null返回对象默认的toString()方法
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	/**
	 * 将数组分割并连接分隔符
	 * 
	 * @param iterator	继承迭代器的数组
	 * @param separator	字节分隔符
	 * @return
	 */
	public static String join(Iterator<?> iterator, char separator) {
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return "";
		}
		Object first = iterator.next();
		if (!iterator.hasNext()) {
			String result = toString(first);
			return result;
		}

		StringBuilder buf = new StringBuilder(256);
		if (first != null) {
			buf.append(first);
		}

		while (iterator.hasNext()) {
			buf.append(separator);
			Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
		}

		return buf.toString();
	}

	/**
	 * 将数组分割并连接分隔符
	 * 
	 * @param iterator	继承迭代器的数组
	 * @param separator	字符串分隔符
	 * @return
	 */
	public static String join(Iterator<?> iterator, String separator) {
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return "";
		}
		Object first = iterator.next();
		if (!iterator.hasNext()) {
			String result = toString(first);
			return result;
		}

		StringBuilder buf = new StringBuilder(256);
		if (first != null) {
			buf.append(first);
		}

		while (iterator.hasNext()) {
			if (separator != null) {
				buf.append(separator);
			}
			Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
		}
		return buf.toString();
	}

	/**
	 * 将数组分割并连接分隔符
	 * 
	 * @param iterable	继承迭代器的数组
	 * @param separator	字节分隔符
	 * @return
	 */
	public static String join(Iterable<?> iterable, char separator) {
		if (iterable == null) {
			return null;
		}
		return join(iterable.iterator(), separator);
	}

	/**
	 * 将数组分割并连接分隔符
	 * 
	 * @param iterable	继承迭代器的数组
	 * @param separator	字符串分隔符
	 * @return
	 */
	public static String join(Iterable<?> iterable, String separator) {
		if (iterable == null) {
			return null;
		}
		return join(iterable.iterator(), separator);
	}
	  
	/**
	 * 去除字符串的前后匹配的字符串
	 * 例如
	 * trim("123321", "1") -> "2332";
	 *
	 * @param s 被处理的字符串
	 * @param c 要匹配的字符串
	 * @return trimmed String.
	 */
	public static String trim(String s, String c){
		int length = s.length();
		if (c == null){
			return s;
		}
		int cLength = c.length();
		if (c.length() == 0){
			return s;
		}
		int start = 0;
		int end = length;
		boolean found; 
		int i;
		found = false;
		for (i=0; !found && i<length; i++){
			char ch = s.charAt(i);
			found = true;
			for (int j=0; found && j<cLength; j++){
				if (c.charAt(j) == ch) found = false;
			}
		}
		if (!found) return "";
		start = i-1;
		found = false;
		for (i=length-1; !found && i>=0; i--){
			char ch = s.charAt(i);
			found = true;
			for (int j=0; found && j<cLength; j++){
				if (c.charAt(j) == ch) found = false;
			}
		}
		end = i+2;
		return s.substring(start, end);
	}
	
	private static final String _BR = "<br/>";
	
	/**
	 * 替换字符串，能能够在HTML页面上直接显示(替换双引号和小于号)
	 * 
	 * @param str
	 * @return
	 */
	public static String showHTML(String str){
		if (str == null) {
			return null;
		}
		str = replaceAll(str, "\r\n", "\n");
		return encodingHTML(str);
	}
	
	/**
	 * 替换字符串，能能够在HTML页面上直接显示(替换双引号和小于号)
	 * 
	 * @param src
	 * @return
	 */
	public static String encodingHTML(String src) {
		if (src == null)
			return "";
		StringBuilder result = new StringBuilder();
		if (src != null) {
			src = src.trim();
			for (int pos = 0; pos < src.length(); pos++) {
				switch (src.charAt(pos)) {
				case '\"':
					result.append("&quot;");
					break;
				case '<':
					result.append("&lt;");
					break;
				case '>':
					result.append("&gt;");
					break;
				case '\'':
					result.append("&apos;");
					break;
				case '&':
					result.append("&amp;");
					break;
				case '%':
					result.append("&pc;");
					break;
				case '_':
					result.append("&ul;");
					break;
				case '#':
					result.append("&shap;");
					break;
				case '?':
					result.append("&ques;");
					break;
				case ' ':
					result.append("&nbsp;");
					break;
				case '\n':
					result.append(_BR);
					break;
				case '\t':
					result.append("&nbsp;&nbsp;&nbsp;&nbsp;");
					break;
				default:
					result.append(src.charAt(pos));
					break;
				}
			}
		}
		return result.toString();
	}

	/**
	 * 反过滤HTML特殊字符
	 * 
	 * @param src
	 * @return
	 */
	public static String decodingHTML(String src) {
		if (src == null)
			return "";
		String result = src;
		result = result.replace("&quot;", "\"").replace("&apos;", "\'");
		result = result.replace("&lt;", "<").replace("&gt;", ">");
		result = result.replace("&amp;", "&").replace("&pc;", "%");
		result = result.replace("&ul", "_").replace("&shap;", "#");
		result = result.replace("&ques", "?").replace("&nbsp;", " ");
		result = result.replace(_BR, "\n");
		return result;
	}
	
	/**
	 * 字符换(人民币数子)转成大写(汉子大写)
	 * 
	 * @param str		数字字符串
	 * @return String 	人民币转换成大写后的字符串
	 */
	public static String hangeToBig(String str) {
		double value;
		try {
			value = Double.parseDouble(str.trim());
		} catch (Exception e) {
			return null;
		}
		char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
		char[] vunit = { '万', '亿' }; // 段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		long midVal = (long) (value * 100); // 转化成整形
		String valStr = String.valueOf(midVal); // 转化成字符串

		String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
		String rail = valStr.substring(valStr.length() - 2); // 取小数部分

		String prefix = ""; // 整数部分转化的结果
		String suffix = ""; // 小数部分转化的结果
		// 处理小数点后面的数
		if (rail.equals("00")) { // 如果小数部分为0
			suffix = "整";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "角"
					+ digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
		}
		// 处理小数点前面的数
		char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
		char zero = '0'; // 标志'0'表示出现过0
		byte zeroSerNum = 0; // 连续出现0的次数
		for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; // 取段内位置
			int vidx = (chDig.length - i - 1) / 4; // 取段位置
			if (chDig[i] == '0') { // 如果当前字符是0
				zeroSerNum++; // 连续0次数递增
				if (zero == '0') { // 标志
					zero = digit[0];
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; // 连续0次数清零
			if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
				prefix += zero;
				zero = '0';
			}
			prefix += digit[chDig[i] - '0']; // 转化该数字表示
			if (idx > 0)
				prefix += hunit[idx - 1];
			if (idx == 0 && vidx > 0) {
				prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
			}
		}

		if (prefix.length() > 0)
			prefix += '圆'; // 如果整数部分存在,则有圆的字样
		return prefix + suffix; // 返回正确表示
	}
	
	/**
	 * 去掉字符串中重复的子字符串
	 * 
	 * 例如: removeSameString("100 100 9658", " ") -> [100, 9658]
	 * 
	 * @param str		原字符串，如果有子字符串则用隔开以表示子字符串
	 * @param sep		分隔符
	 * @return String 	返回去掉重复子字符串后的字符串
	 */
	@SuppressWarnings("unused")
	private static String removeSameString(String str, String sep) {
		Set<String> mLinkedSet = new LinkedHashSet<String>();// set集合的特征：其子集不可以重复
		String[] strArray = str.split(" ");// 根据空格(正则表达式)分割字符串
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < strArray.length; i++) {
			if (!mLinkedSet.contains(strArray[i])) {
				mLinkedSet.add(strArray[i]);
				sb.append(strArray[i] + " ");
			}
		}
		return sb.toString();
	}

	//判读字符串是否全部是大写字母
	public static boolean isAcronym(String word)
	{
		for(int i = 0; i < word.length(); i++)
		{
			char c = word.charAt(i);
			if (!Character.isUpperCase(c))
			{
				return false;
			}
		}
		return true;
	}

}
