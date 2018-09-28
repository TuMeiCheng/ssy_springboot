package com.wande.ssy.dubbo.provider.service;

import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.ItemFlow;
import com.wande.ssy.entity.ItemflowExt;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;

import java.util.Map;


 /**
  * ItemflowService
  * 
  * @author vwFisher(422578659@qq.com)
  * 2017年1月11日下午8:50:11
  */
public interface ItemflowService {
	
	//======================器材巡检部分=======================
	/**
	 * 添加器材巡检
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Boolean> addItemflow(ItemFlow obj);
	
	
	/**
	 * 修改器材巡检
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Boolean> updateItemflow(ItemFlow obj);
	
	
	/**
	 * 根据ID获取器材巡检
	 * 
	 * @param itemflowId
	 * @return
	 */
	public RespWrapper<ItemFlow> getOneItemflow(int itemflowId);
	
	/**
	 * 根据followId获取器材巡检
	 * 
	 * @param followId 根据followId查找对应的巡检记录
	 * @return
	 */
	public RespWrapper<ItemFlow> getOneItemflowByFollowId(int followId);
	
	
	/**
	 * 多参数获取器材巡检列表
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public RespWrapper<DataPage<ItemflowExt>> getItemflowByPage(Admin admin, Map<String, Object> params, int pageNo, int pageSize);
	
	/**
	 * 根据传入的itemflowIds获取Map<itemflowId, Itemflow>的数据
	 * 
	 * @param itemflowIds
	 * @return
	 */
	public RespWrapper<Map<Integer, ItemFlow>> getItemflowMapInIds(String itemflowIds);
}
