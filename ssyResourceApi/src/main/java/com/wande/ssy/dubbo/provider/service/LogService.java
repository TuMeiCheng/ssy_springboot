package com.wande.ssy.dubbo.provider.service;

import com.wande.ssy.entity.Log;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;

import java.util.Map;

/**
 * LogService
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月11日下午8:50:29
 */
public interface LogService {
	
	//======================操作日志部分=======================
	
	/**
	 * 添加日志
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Boolean> addLog(Log obj);
	
	/**
	 * 多参数获取操作日志列表
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public RespWrapper<DataPage<Log>> getLogByPage(Map<String, Object> params, int pageNo, int pageSize);
}