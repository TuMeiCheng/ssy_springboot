package com.wande.ssy.dubbo.provider.service;

import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Export;
import com.wande.ssy.entity.Qrcode;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;

import java.util.List;
import java.util.Map;

/**
 * QrcodeService
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月11日下午8:50:29
 */
public interface QrcodeService {
	
	//======================二维码表部分=======================
	/**
	 * 添加二维码表
	 * 
	 * @param qrcodeNum		生成二维码数量
	 * @param areaId		场地ID
	 * @param admin			用户
	 * @param isAreaQrcode	是否是场地二维码
	 * @param standardcode	器材标准
	 * @return
	 */
	public RespWrapper<String> addQrcode(int qrcodeNum, int areaId, int standardcode, Admin admin, int isAreaQrcode);
	
	/**
	 * 多参数获取二维码表列表
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public RespWrapper<DataPage<Qrcode>> getQrcodeByPage(Map<String, Object> params, int pageNo, int pageSize);
	
	/**
	 * 删除二维码
	 * 
	 * @param qrcodeId	二维码ID
	 * @return
	 */
	public RespWrapper<Boolean> deleteQrcode(int qrcodeId);
	
	/**
	 * 导出未出厂的二维码
	 * 
	 * @return
	 */
	public RespWrapper<List<Qrcode>> getExportList();
	
	/**
	 * 修改二维码状态
	 * 
	 * @param status
	 * @param qrcodeIds
	 * @return
	 */
	public RespWrapper<Boolean> updateStatue(int status, String qrcodeIds);
	
	/**
	 * 添加一条导出记录
	 * 
	 * @param qrcodeIds
	 * @return
	 */
	public RespWrapper<Boolean> addExport(Export obj, String qrcodeIds);
	
	/**
	 * 获取导出记录
	 * 
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public RespWrapper<DataPage<Export>> getExportByPage(Map<String, Object> params, int pageNo, int pageSize);
}
