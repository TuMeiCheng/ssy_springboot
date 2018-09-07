package com.wande.ssy.dubbo.provider.service;

import com.wande.ssy.entity.ItemRepair;
import com.ynm3k.mvc.model.RespWrapper;

/**
 * ItemrepairService
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月11日下午8:35:26
 */
public interface ItemrepairService {
	
	//======================器材网路报修部分=======================
	/**
	 * 添加器材网路报修
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Boolean> addItemrepair(ItemRepair obj);
	
	
	/**
	 * 修改器材网路报修
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Boolean> updateItemrepair(ItemRepair obj);
	
	
	/**
	 * 根据ID获取器材网路报修
	 * 
	 * @param repairId
	 * @return
	 */
	public RespWrapper<ItemRepair> getOneItemRepair(int repairId);
	
	
//	/**
////	 * 多参数获取器材网路报修列表
////	 *
////	 * @param params
////	 * @param pageNo
////	 * @param pageSize
////	 * @return
////	 */
////	public RespWrapper<DataPage<ItemRepairE>> getItemRepairByPage(Admin admin, Map<String, Object> params, int pageNo, int pageSize);
}