package com.wande.ssy.dubbo.provider.service;

import com.wande.ssy.entity.RegionAgency;
import com.ynm3k.mvc.model.RespWrapper;

/**
 * 管理公司和其管理地区的的服务
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月11日下午3:22:34
 */
public interface RegionagencyService {
	
	/**
	 * 根据uin获取对应的regionagency的信息
	 * 
	 * @param uin
	 * @return
	 */
	public RespWrapper<RegionAgency> getOneRegionagencyByAgencyId(long uin);
}
