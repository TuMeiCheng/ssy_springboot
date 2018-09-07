package com.wande.ssy.dubbo.provider.service;

import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.AreaFlow;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;

import java.util.Map;



public interface AreaflowService {
	
	//======================场地巡检部分=======================
	/**
	 * 添加场地巡检
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Boolean> addItemflow(AreaFlow obj);
	
	
	/**
	 * 修改场地巡检
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Boolean> updateItemflow(AreaFlow obj);
	
	
	/**
	 * 根据ID获取场地巡检
	 * 
	 * @param id
	 * @return
	 */
	public RespWrapper<AreaFlow> getOneItemflow(int id);
	
	/**
	 * 根据followId获取场地巡检
	 * 
	 * @param followId 根据followId查找对应的巡检记录
	 * @return
	 */
	public RespWrapper<AreaFlow> getOneAreaflowByFollowId(int followId);
	
	
	/**
	 * 多参数获取器材巡检列表
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public RespWrapper<DataPage<AreaFlow>> getAreaflowByPage(Admin admin, Map<String, Object> params, int pageNo, int pageSize);
	
	/**
	 * 根据传入的Areaflowids获取Map<itemflowId, Itemflow>的数据
	 * 
	 * @param AreaflowIds
	 * @return
	 */
	public RespWrapper<Map<Integer, AreaFlow>> getAreaflowMapInIds(String AreaflowIds);
}
