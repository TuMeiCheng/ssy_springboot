package com.wande.ssy.dubbo.provider.service;

import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Region;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;

import java.util.List;
import java.util.Map;

/**
 * RegionService
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月11日下午8:50:29
 */
public interface RegionService {
	
	//======================地区表部分=======================
	/**
	 * 添加地区表
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Integer> addRegion(Region obj);
	
	
	/**
	 * 修改地区表
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Boolean> updateRegion(Region obj);
	
	
	/**
	 * 根据ID获取地区表
	 * 
	 * @param regionId
	 * @return
	 */
	public RespWrapper<Region> getOneRegion(int regionId);
	
	
	/**
	 * 多参数获取地区表列表
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public RespWrapper<DataPage<Region>> getRegionByPage(Map<String, Object> params, int pageNo, int pageSize);
	
	/**
	 * 获取region列表
	 * @param admin
	 * @param parentId	Integer.MAX_VALUE代表获取全部, 有值获取对应pid的region列表
	 * @return
	 */
	public RespWrapper<List<Region>> getRegionListByParentId(Admin admin, int parentId);
	
	/**
	 * 根据regionId获取它所有下级地区的列表
	 * 
	 * @param regionId
	 * @return
	 */
	public RespWrapper<List<Region>> getRegionList(int regionId);
}
