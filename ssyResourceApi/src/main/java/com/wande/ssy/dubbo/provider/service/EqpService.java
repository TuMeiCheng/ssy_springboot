package com.wande.ssy.dubbo.provider.service;

import com.wande.ssy.entity.Eqp;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;

import java.util.List;
import java.util.Map;

/**
 * EqpService
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月11日下午8:35:26
 */
public interface EqpService {
	
	//======================器材库部分=======================
	/**
	 * 添加器材库
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Integer> addEqp(Eqp obj);
	
	
	/**
	 * 修改器材库
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Boolean> updateEqp(Eqp obj);
	
	
	/**
	 * 根据ID获取器材库
	 * 
	 * @param eqpId
	 * @return
	 */
	public RespWrapper<Eqp> getOneEqp(int eqpId);
	
	
	/**
	 * 多参数获取器材库分页列表
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public RespWrapper<DataPage<Eqp>> getEqpByPage(Map<String, Object> params, int pageNo, int pageSize);
	
	
	/**
	 * 多参数获取器材库列表
	 * 
	 * @param
	 * @return
	 */
	public RespWrapper<List<Eqp>> getEqpByList(int supplierId, int eqpsortId);
	
	/**
	 * 根据传入的eqpIds获取Map<eqpId, Eqp>的数据
	 * 
	 * @param eqpIds
	 * @return
	 */
	public RespWrapper<Map<Integer, Eqp>> getEqpMapInIds(String eqpIds);
}