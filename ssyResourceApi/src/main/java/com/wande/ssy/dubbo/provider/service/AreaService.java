package com.wande.ssy.dubbo.provider.service;

import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Area;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;

import java.util.List;
import java.util.Map;

/**
 * AreaService
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月11日下午8:35:26
 */
public interface AreaService {
	
	//======================场地部分=======================
	/**
	 * 添加场地
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Integer> addArea(Area obj);
	
	
	/**
	 * 修改场地
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Boolean> updateArea(Area obj);
	
	
	/**
	 * 根据ID获取场地
	 * 
	 * @param areaId
	 * @return
	 */
	public RespWrapper<Area> getOneArea(int areaId);
	
	
	/**
	 * 多参数获取场地列表
	 * 
	 * @param admin		当前登录系统用户,用于数据过滤
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public RespWrapper<DataPage<Area>> getAreaByPage(Admin admin, Map<String, Object> params, int pageNo, int pageSize);
	
	/**
	 * 根据登录系统用户获取场地下拉选
	 * 
	 * @param admin
	 * @return
	 */
	public RespWrapper<List<Area>> getAreaSelect(Admin admin);
	
	/**
	 * 根据传入的areaIds获取Map<areaId, Area>的数据
	 * 
	 * @param areaIds
	 * @return
	 */
	public RespWrapper<Map<Integer, Area>> getAreaMapInIds(String areaIds);
	
	/**
	 * 根据传入的qrcode获取area的数据
	 * 
	 * @param qrcode
	 * @return
	 */
	public RespWrapper<Map<String, Object>> getAreaDetailsByQrcode(String qrcode);
	/**
	 * 修改场地状态
	 * 
	 * @param admin      市民报修 Admix参数为null
	 * @param itemId
	 * @param status
	 * @return
	 */
	public RespWrapper<Boolean> updateAreaState(Admin admin, int itemId, int status);
	
	
	
	
	
}