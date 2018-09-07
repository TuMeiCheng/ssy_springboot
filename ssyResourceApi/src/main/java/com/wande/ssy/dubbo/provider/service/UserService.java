package com.wande.ssy.dubbo.provider.service;

import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Item;
import com.wande.ssy.entity.User;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;

import java.util.List;
import java.util.Map;

/**
 * UserService
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月11日下午8:50:29
 */
public interface UserService {
	/**
	 * 添加APP用户
	 * 
	 * @param obj	User对象
	 * @return RespWrapper
	 */
	 RespWrapper<User> addUser(User obj);
	
	/**
	 * 更新APP用户信息
	 * 
	 * @param obj	User对象
	 * @return RespWrapper
	 */
	 RespWrapper<Boolean> updateUser(User obj);
	
	/**
	 * 根据用户sid获取APP用户信息
	 * 
	 * @param sid
	 * @return RespWrapper
	 */
	 RespWrapper<User> getUserBySid(String sid);
	
	
	/**
	 * 根据uin获取APP用户
	 * 
	 * @param uin
	 * @return
	 */
	 RespWrapper<User> getOneUser(long uin);
	
	/**
	 * 直接重置用户密码
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	 RespWrapper<Boolean> resetPwd(String account, String password);
	
	
	/**
	 * 通过旧密码修改密码
	 *
	 * @param phone		手机账号
	 * @param oldPwd	旧密码
	 * @param newPwd	新密码
	 * @return
	 */
	 RespWrapper<Boolean> changePwd(String phone, String oldPwd, String newPwd);
	
	/**
	 * 获取APP用户列表
	 * 
	 * @param params
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	 RespWrapper<DataPage<User>> getUserByPage(Map<String, Object> params, int pageNo, int pageSize);
	
	
	/**
	 * 根据当前登录用户获取器材的,安装负责人, 巡检负责人, 维修负责人下拉选
	 * @param admin
	 * @return
	 */
	 RespWrapper<List<Item>> getItemBySelect(Admin admin);
	
	/**
	 * 根据传入的userIds获取Map<uin, User>的数据
	 * 
	 * @param userIds
	 * @return
	 */
	 RespWrapper<Map<Long, User>> getUserMapInIds(String userIds);
	
	/**
	 * 根据传入的userIds获取List<User>的数据
	 * 
	 * @param userIds
	 * @return
	 */
	 RespWrapper<List<User>> getUserListInIds(String userIds);
}
