package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.UserDao;
import com.wande.ssy.dubbo.provider.service.UserService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Item;
import com.wande.ssy.entity.User;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;


	/* 添加用户
	 * @param: [obj, pwd]
	 * @return: com.ynm3k.mvc.model.RespWrapper<java.lang.Long> */
	@Override
	public RespWrapper<User> addUser(User user) {
		if (this.userDao.checkUserPhone(user.getPhone())) {
			return RespWrapper.makeResp(1001, "该手机账号已经存在!", null);
		}
		//否则保存到数据库
		Boolean bln = this.userDao.addUser(user);
		System.out.println(bln);

		return RespWrapper.makeResp(0, "成功添加系统账号："+user.getPhone(), user);
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
