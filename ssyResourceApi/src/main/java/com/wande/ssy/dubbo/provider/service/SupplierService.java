package com.wande.ssy.dubbo.provider.service;

import com.wande.ssy.entity.Supplier;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;

import java.util.List;
import java.util.Map;

/**
 * SupplierService
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月11日下午8:50:29
 */
public interface SupplierService {
	
	//======================器材供应商部分=======================
	/**
	 * 添加器材供应商
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Integer> addSupplier(Supplier obj);
	
	
	/**
	 * 修改器材供应商
	 * 
	 * @param obj
	 * @return
	 */
	public RespWrapper<Boolean> updateSupplier(Supplier obj);
	
	
	/**
	 * 根据ID获取器材供应商
	 * 
	 * @param supplierId
	 * @return
	 */
	public RespWrapper<Supplier> getOneSupplier(int supplierId);
	
	/**
	 * 获取供应商列表(下拉选)
	 * 
	 * @return
	 */
	public RespWrapper<List<Supplier>> getSupplierList();
	
	
	/**
	 * 多参数获取器材供应商列表
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public RespWrapper<DataPage<Supplier>> getSupplierByPage(Map<String, Object> params, int pageNo, int pageSize);
	
	/**
	 * 根据传入的supplierIds获取Map<supplierId, Supplier>的数据
	 * 
	 * @param supplierIds
	 * @return
	 */
	public RespWrapper<Map<Integer, Supplier>> getSupplierMapInIds(String supplierIds);
}
