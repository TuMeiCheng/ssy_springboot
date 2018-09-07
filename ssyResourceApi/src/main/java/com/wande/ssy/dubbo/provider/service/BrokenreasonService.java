package com.wande.ssy.dubbo.provider.service;

import com.wande.ssy.entity.Brokenreason;
import com.ynm3k.mvc.model.RespWrapper;

import java.util.List;
import java.util.Map;

/**
 * BrokenreasonService
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月11日下午8:35:26
 */
public interface BrokenreasonService {
	
	/**
	 * 根据reasonId获取原因
	 * 
	 * @param reasonId
	 * @return
	 */
	public RespWrapper<Brokenreason> getOneBrokenreason(int reasonId);
			
	
	/**
	 * 获取报修原因所有列表
	 * 
	 * @param parentId	上级ID, Integer.MAX_VALUE 则返回所有报修原因
	 * @return
	 */
	public RespWrapper<List<Brokenreason>> getBrokenreasonList(int parentId);
	
	/**
	 * 根据传入的reasonIds获取Map<reasonId, Brokenreason>的数据
	 * 
	 * @param reasonIds
	 * @return
	 */
	public RespWrapper<Map<Integer, Brokenreason>> getBrokenreasonMapInIds(String reasonIds);
}
