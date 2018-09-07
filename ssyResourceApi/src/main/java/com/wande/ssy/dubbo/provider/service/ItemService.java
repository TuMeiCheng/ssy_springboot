package com.wande.ssy.dubbo.provider.service;

import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Item;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;

import java.util.Map;
/**
 * ItemService
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月11日下午8:50:29
 */
public interface ItemService {
	
	//======================器材部分=======================
	/**
	 * 添加器材
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Boolean> addItem(Item obj);
	
	
	/**
	 * 修改器材
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Boolean> updateItem(Item obj);
	
	
	/**
	 * 根据ID获取器材
	 * 
	 * @param itemId
	 * @return
	 */
	public RespWrapper<Item> getOneItem(int itemId);
	
	/**
	 * 根据器材编号获取器材
	 * 
	 * @param itemsn
	 * @return
	 */
	public RespWrapper<Item> getOneItemByItemsn(String itemsn);
	
	/**
	 * 更新器材的状态
	 * 
	 * @param admin	当前登录角色,如果是市民,则为null
	 * @param itemId
	 * @param status
	 * @return
	 */
	public RespWrapper<Boolean> updateItemStatus(Admin admin, int itemId, int status);
	
	/**
	 * 多参数获取器材列表
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public RespWrapper<DataPage<Item>> getItemByPage(Admin admin, Map<String, Object> params, int pageNo, int pageSize);
}