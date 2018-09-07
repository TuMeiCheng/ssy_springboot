package com.wande.ssy.dubbo.provider.service;

import com.wande.ssy.entity.Org;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;

import java.util.List;
import java.util.Map;

/**
 * OrgService
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月11日下午8:50:29
 */
public interface OrgService {
	
	//======================体育局表部分=======================
	/**
	 * 添加管辖机构
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Integer> addOrg(Org obj);
	
	
	/**
	 * 修改管辖机构
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Boolean> updateOrg(Org obj);
	
	
	/**
	 * 根据ID获取管辖机构
	 * 
	 * @param orgId
	 * @return
	 */
	public RespWrapper<Org> getOneOrg(int orgId);
	
	/**
	 * 根据地区ID获取管辖机构
	 * 
	 * @param regionId
	 * @return
	 */
	public RespWrapper<Org> getOneOrgByRegionId(int regionId);
	
	/**
	 * 多参数获取管辖机构列表(分页)
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public RespWrapper<DataPage<Org>> getOrgByPage(Map<String, Object> params, int pageNo, int pageSize);
	
	/**
	 * 获取管辖机构列表(不分页)
	 * 
	 * @param parentId	Integer.MAX_VALUE 获取全部
	 * @return
	 */
	public RespWrapper<List<Org>> getOrgList(int parentId);
	
	/**
	 * 根据传入的orgIds获取Map<oegId, Org>的数据
	 * 
	 * @param orgIds
	 * @return
	 */
	public RespWrapper<Map<Integer, Org>> getOrgMapInIds(String orgIds);
}
