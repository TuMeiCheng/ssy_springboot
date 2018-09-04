package com.wande.ssy.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.*;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = IUserBussness.class)
public class UserBussnessImpl implements IUserBussness {

	public String getInfo(Integer userId) {
		Log l = new Log().findById(17);
		System.out.println(JSON.toJSONString(new Log().findById(17)));
		return "用户信息:" + userId;
	}

	@Override
	public RespWrapper<Long> addUser(User obj, String pwd) {
		User user = new User().findFirst("SELECT * FROM eqp_user where phone = ?",obj.getPhone());
		if (user != null) {
			System.out.println("该手机已经注册");
			return RespWrapper.makeResp(1001, "该手机账号已经存在!", null);
		}
		//否则保存到数据库
		System.out.println("可以注册");
		return RespWrapper.makeResp(1001, "该手机账号可以注册!", null);
	}

	@Override
	public RespWrapper<Boolean> updateUser(User obj) {
		return null;
	}

	@Override
	public RespWrapper<User> getUserBySid(String sid) {
		return null;
	}

	@Override
	public RespWrapper<User> getOneUser(long uin) {
		return null;
	}

	@Override
	public RespWrapper<Boolean> resetPwd(String account, String password) {
		return null;
	}

	@Override
	public RespWrapper<Boolean> changePwd(String phone, String oldPwd, String newPwd) {
		return null;
	}

	@Override
	public RespWrapper<DataPage<User>> getUserByPage(Map<String, Object> params, int pageNo, int pageSize) {
		return null;
	}

	@Override
	public RespWrapper<List<Item>> getItemBySelect(Admin admin) {
		return null;
	}

	@Override
	public RespWrapper<Map<Long, User>> getUserMapInIds(String userIds) {
		return null;
	}

	@Override
	public RespWrapper<List<User>> getUserListInIds(String userIds) {
		return null;
	}


}
