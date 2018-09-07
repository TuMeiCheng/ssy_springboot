package com.wande.ssy.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Web项目路径工具类
 * 
 * PathUtil.class.getResource("/").toURI().getPath()
 *     ->  ../WebRoot/WEB-INF/classes/
 * ->getParentFile().getParentFile() -> ../WebRoot/
 * 
 * @author vwFisher(422578659@qq.com)
 * 2016年11月14日下午11:31:19
 */
public class PathUtil {

	/**
	 * 获取classes路径
	 * @return
	 */
	public static String getRootClassPath() {
		try {
			String path = PathUtil.class.getResource("/").toURI().getPath();
			return new File(path).getCanonicalPath();
			//String path = PathUtil.class.getClassLoader().getResource("").toURI().getPath();
			//return new File(path).getAbsolutePath();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取WebRoot路径
	 * @return
	 */
	public static String getWebRootPath() {
		try {
			String path = PathUtil.class.getResource("/").toURI().getPath();
			//getAbsolutePath(); 返回的是定义时的路径对应的相对路径，但不会处理“.”和“..”的情况 	
			//       E:\workspace\Test\.\test.txt 
			//getCanonicalPath();返回的是规范化的绝对路径，相当于将getAbsolutePath()中的“.”和“..”解析成对应的正确的路径
			//       E:\workspace\Test\test.txt  
			return new File(path).getParentFile().getParentFile().getCanonicalPath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 判断是否是绝对路径
	 * @param path
	 * @return
	 */
	public static boolean isAbsolutelyPath(String path) {
		return path.startsWith("/") || path.indexOf(":") == 1;
	}
	
	/**
	 * 返回WebRoot路径
	 * E:\workspace\service_eqp\WebRoot
	 * 
	 * @param request
	 * @return
	 */
	public static String getWebRootPath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/");
	}
	
	/**
	 * 返回域名地址
	 * http://127.0.0.1:90/
	 * 
	 * @param request
	 * @return
	 */
	public static String getHostPath(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();  
		return url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
	}

	/**
	 * 返回域名地址(去掉后面的斜杠)
	 * http://127.0.0.1:90
	 * 
	 * @param request
	 * @return
	 */
	public static String getHostPathExt(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();  
		String hostPath = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
		return hostPath.substring(0, hostPath.length()-1);
	}

	public static void main(String[] args) {
		System.out.println(getWebRootPath());
	}
}
