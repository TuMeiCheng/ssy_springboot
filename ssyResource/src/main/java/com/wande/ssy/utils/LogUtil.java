package com.wande.ssy.utils;

import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Log;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志工具
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月12日下午5:20:02
 */
public class LogUtil {

	public static Log getLog(Admin admin, HttpServletRequest request, String desc) {
		Log log = new Log();
		log.setCode(request.getRequestURI());
		log.setDesc(desc);
		log.setAccount(admin.getAccount());
		log.setIp(request.getRemoteAddr());
		log.setCreateTime(System.currentTimeMillis());
		log.setCreateBy(admin.getUin());
		return log;
	}	
}
