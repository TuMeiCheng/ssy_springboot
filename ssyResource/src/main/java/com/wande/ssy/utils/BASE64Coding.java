package com.wande.ssy.utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * BASE64工具类
 * 对JAVA原生态BASE64处理做了封装,不抛异常
 * 
 * @author Dark Light
 * 2016年11月4日下午5:01:16
 */
public class BASE64Coding {   
	
    /**
     * 按系统默认编码encode字符串
     * 
     * @param str		
     * @return String
     */
    public static String encode (String str) {
    	String result = new BASE64Encoder().encode(str.getBytes());
        return result.replaceAll("\\r\\n", "");
    }
    
    
    /**
     * 按指定编码encode字符串
     * 
     * @param str
     * @param charsetName
     * @return String
     */
    public static String encode (String str, String charsetName) {
    	String result = "";
    	Charset charset = Charset.forName(charsetName);
    	if(charset != null){
    		result = new BASE64Encoder().encode(str.getBytes(charset));
    	}else{
    		result = new BASE64Encoder().encode(str.getBytes());
    	}
    	return result.replaceAll("\\r\\n", "");
    }
    
    
    /**
     * 对字节数组进行encode
     * 
     * @param bytes
     * @return String
     */
    public static String encode(byte[] bytes) {
        String result = new BASE64Encoder().encode(bytes);
        return result.replaceAll("\\r\\n", "");
    }
    
    
    /**
     * 对ByteBuffer进行encode
     * 
     * @param buf
     * @return String
     */
    public static String encode(ByteBuffer buf) {
        String result = new BASE64Encoder().encode(buf);
        return result.replaceAll("\\r\\n", "");
    }
    
    
    
    /**
     * 对BASE64的字符串进行decode,若decode失败返回null
     * Author：</b>Dark Light<br>
     * Date：</b>2016年9月3日<br>
     * 
     * @param str
     * 
     * @return byte[]
     */
    public static byte[] decodeBuffer(String str) {
        try {
            return new BASE64Decoder().decodeBuffer(str);
        } catch(Exception e) {
            return null;
        }
    }
    
    
    /**
     * 按系统默认编码decode字符串,若decode失败返回""
     * Author：</b>Dark Light<br>
     * Date：</b>2016年9月3日<br>
     * 
     * @param str
     * 
     * @return String
     */
    public static String decode(String str) {
    	String result = "";
        try {
            byte[] bytes = new BASE64Decoder().decodeBuffer(str);
            result = new String(bytes);
        } catch(Exception e) {
        	e.printStackTrace();
        }
        return result;
    }
    
    
    /**
     * 按指定编码decode字符串,若decode失败返回""
     * 
     * @param str
     * @param charsetName
     * @return String
     */
    public static String decode(String str, String charsetName) {
    	String result = "";
    	try {
    		Charset cs = Charset.forName(charsetName);
    		if(cs != null){
    			byte[] bytes = new BASE64Decoder().decodeBuffer(str);
    			result = new String(bytes, charsetName);
    		} else {
    			result = decode(str);
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return result;
    }
    
    public static void main(String[] args) {
    	String s = "123456jahahuhai奥哈哈啦啦啦2222222oojsdfdas\nfdsfsd\nfdsfsdsdfdsfdsfs\nszad a 哈哈规划dfsdafasdfsdfasdfasdfsdafsdfsdafsdffsdfdsfsdfsdfsd";
    	String str = BASE64Coding.encode(s);
    	System.out.println(str);
    	System.out.println(BASE64Coding.decode(str));
	}
}
