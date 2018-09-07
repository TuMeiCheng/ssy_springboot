package com.wande.ssy.dubbo.provider.service;

import com.wande.ssy.entity.EqpSort;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;

import java.util.List;
import java.util.Map;

/**
 * EqpsortService
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月11日下午8:35:26
 */
public interface EqpsortService {
	
	//======================器材分类表部分=======================
	/**
	 * 添加器材分类表
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Integer> addEqpsort(EqpSort obj);
	
	
	/**
	 * 修改器材分类表
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Boolean> updateEqpsort(EqpSort obj);
	
	
	/**
	 * 根据ID获取器材分类表
	 * 
	 * @param EqpSortId
	 * @return
	 */
	public RespWrapper<EqpSort> getOneEqpSort(int EqpSortId);
	
	
	/**
	 * 多参数获取器材分类表列表
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public RespWrapper<DataPage<EqpSort>> getEqpSortByPage(Map<String, Object> params, int pageNo, int pageSize);

	/**
	 * 根据parentId获取下拉选列表
	 * 
	 * @return
	 */
	public RespWrapper<List<EqpSort>> getEqpSortList(int parentId);
	
	/**
	 * 根据传入的EqpSortIds获取Map<EqpSortId, EqpSort>的数据
	 * 
	 * @param EqpSortIds
	 * @return
	 */
	public RespWrapper<Map<Integer, EqpSort>> getEqpSortMapInIds(String EqpSortIds);
}
