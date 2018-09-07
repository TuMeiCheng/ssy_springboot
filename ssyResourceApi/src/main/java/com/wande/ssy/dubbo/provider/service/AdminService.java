package com.wande.ssy.dubbo.provider.service;

import com.wande.ssy.entity.Admin;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;

import java.util.Map;

/**
 * 系统用户Service接口
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月11日下午8:35:26
 */
public interface AdminService {
	
	/**
	 * 添加系统用户(系统用户和体育局账号, 管理公司需要传多一个字段)
	 * 
	 * @param obj	Admin对象
	 * @param pwd	密码
	 * @param regionIds	管理公司需要传对应管辖区域
	 * @return
	 */
	public RespWrapper<Long> addAdmin(Admin obj, String pwd, String regionIds);
	
	/**
	 * 更新用户信息
	 * 
	 * @param obj	Admin对象
	 * @param regionIds	管理公司需要传对应管辖区域
	 * @return
	 */
	public RespWrapper<Boolean> updateAdmin(Admin obj, String regionIds);
	
	/**
	 * 根据用户sid获取用户信息
	 * 
	 * @param sid
	 * @return
	 */
	public RespWrapper<Admin> getAdminBySid(String sid);  
	
	
	/**
	 * 根据uin获取用户信息
	 * 
	 * @param uin
	 * @return
	 */
	public RespWrapper<Admin> getAdminByUin(long uin);
	
	
	/**
	 * 用户登陆
	 * 
	 * @param account 账号
	 * @param password 密码
	 * @param vCode 验证码
	 * @return
	 */
	public RespWrapper<String> login(String account, String password, String vCode);
	
	
	/**
	 * 验证用户是否存在
	 * 
	 * @param account
	 * @return true=存在，false=不存在
	 */
	public RespWrapper<Boolean> checkAccount(String account);
	
	
	/**
	 * 直接重置用户密码
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	public RespWrapper<Boolean> resetPwd(String account, String password);
	
	
	/**
	 * 通过旧密码修改密码
	 *
	 * @param account	账号
	 * @param oldPwd	旧密码
	 * @param newPwd	新密码
	 * @return
	 */
	public RespWrapper<Boolean> changePwd(String account, String oldPwd, String newPwd);
	
	/**
	 * 获取后台用户列表
	 * 
	 * @param params
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public RespWrapper<DataPage<Admin>> getAdminByPage(Map<String, Object> params, int pageNo, int pageSize);
	
	/**
	 * 根据传入的adminIds获取Map<uin, Admin>的数据
	 * 
	 * @param adminIds
	 * @return
	 */
	public RespWrapper<Map<Long, Admin>> getAdminMapInIds(String adminIds);
}
