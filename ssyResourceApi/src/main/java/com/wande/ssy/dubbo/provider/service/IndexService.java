package com.wande.ssy.dubbo.provider.service;

import com.wande.ssy.entity.Admin;
import com.ynm3k.mvc.model.RespWrapper;
import com.ynm3k.mvc.model.SuperBean;

import java.util.List;
import java.util.Map;

/**
 * 首页报表Service接口
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月11日下午8:35:26
 */
public interface IndexService {
	
	/**
	 * 获取首页场地和器材数量
	 * @param admin		当前登录对象
	 * @param params	带参数进行数据筛选
	 * @return
	 */
	public RespWrapper<SuperBean> getAreaItemCount(Admin admin, Map<String, Object> params);
	
	/**
	 * 一、安装地点分布
	 * 
	 * @param admin		当前登录对象
	 * @param params	带参数进行数据筛选
	 * @return
	 */
	public RespWrapper<SuperBean> getInstallAreaChart(Admin admin, Map<String, Object> params);
	
	/**
	 * 二、供货商器材数量
	 * 
	 * @param admin		当前登录对象
	 * @param params	带参数进行数据筛选
	 * @return
	 */
	public RespWrapper<List<SuperBean>> getSupplierChart(Admin admin, Map<String, Object> params);
	
	/**
	 * 三、设备安装年份
	 * 
	 * @param admin		当前登录对象
	 * @param params	带参数进行数据筛选
	 * @return
	 */
	public RespWrapper<List<SuperBean>> getEqpTimeChart(Admin admin, Map<String, Object> params);
	
	/**
	 * 四、设施状态分布
	 * 
	 * @param admin		当前登录对象
	 * @param params	带参数进行数据筛选
	 * @return
	 */
	public RespWrapper<List<SuperBean>> getEqpStatusChart(Admin admin, Map<String, Object> params);
	
	/**
	 * 五、设施安全警示接口
	 * 
	 * @param admin		当前登录对象
	 * @param params	带参数进行数据筛选
	 * @return
	 * [{time:: 2011-01, num:9},{time:: 2011-02, num:9},]
	 */
	public RespWrapper<List<SuperBean>> getEqpSafeChart(Admin admin, Map<String, Object> params);
}
