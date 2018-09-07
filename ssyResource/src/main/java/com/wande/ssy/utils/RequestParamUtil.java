package com.wande.ssy.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 * 请求工具类
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年2月8日 下午1:51:10
 */
public class RequestParamUtil {

	/**
	 * 打印请求的头部信息到控制台中, 适合开发模式使用
	 * 打印格式为
	 * ==========请求头中header的参数列表==========
	 * [name=key, value=value]
	 * 
	 * @param request
	 */
	public static void printHeaders(HttpServletRequest request) {
		System.out.println("==========请求头中header的参数列表==========");
		System.out.println("==========请求的URL:" + request.getRequestURI());
	    Enumeration<?> enums = request.getHeaderNames();
	    while (enums.hasMoreElements()) {
	    	String key = (String) enums.nextElement();
	    	String value = request.getHeader(key);
			System.out.println("[name="+key+",value="+value+"]");
	    }
	}
	
	/**
	 * 打印请求的参数信息到控制台中, 适合开发模式使用
	 * 打印格式为
	 * ==========请求头中提交的params参数列表==========
	 * [name=key, value=value]
	 * 
	 * @param request
	 */
	public static void printParameters(HttpServletRequest request) {
		System.out.println("==========请求头中提交的params参数列表==========");
		System.out.println("==========请求的URL:" + request.getRequestURI());
		Enumeration<String> enums = request.getParameterNames();
		while(enums.hasMoreElements()) {
			String key = enums.nextElement();
	    	String value = request.getParameter(key);
			System.out.println("[name="+key+",value="+value+"]");
		}
	}
	
	/**
	 * 打印请求头中的body信息, 慎用, 如果上传文件的话, 很容易打爆
	 * 打印格式:
	 * ==========请求头中提交的body内容==========
	 * [body的大小: size]
	 * [body的内容: res]
	 * 
	 * @param request
	 */
	public static void printBody(HttpServletRequest request) {
		System.out.println("==========请求头中提交的body内容==========");
		System.out.println("==========请求的URL:" + request.getRequestURI());
		try {
			int size = request.getContentLength();
			System.out.println("[body的大小:" + size + "]");
			InputStream is = request.getInputStream();
			byte[] reqBodyBytes = readBytes(is, size);
			String res = new String(reqBodyBytes);
			System.out.println("[body的内容:" + res + "]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取流, 返回byte[]的数组数据
	 * 
	 * @param is
	 * @param contentLen
	 * @return
	 */
	public static final byte[] readBytes(InputStream is, int contentLen) {
		if (contentLen > 0) {
			int readLen = 0;
			int readLengthThisTime = 0;
			byte[] message = new byte[contentLen];
			try {
				while (readLen != contentLen) {
					readLengthThisTime = is.read(message, readLen, contentLen - readLen);
					if (readLengthThisTime == -1) {// Should not happen.
						break;
					}
					readLen += readLengthThisTime;
				}
				return message;
			} catch (IOException e) {
				// Ignore
				// e.printStackTrace();
			}
		}
		return new byte[] {};
	}
}
