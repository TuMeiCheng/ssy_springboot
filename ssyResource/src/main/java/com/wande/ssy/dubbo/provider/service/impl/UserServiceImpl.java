package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.ItemDao;
import com.wande.ssy.dao.UserDao;
import com.wande.ssy.dubbo.provider.service.UserService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.Item;
import com.wande.ssy.entity.User;
import com.wande.ssy.enums.AdminStatus;
import com.wande.ssy.utils.CopyPropertiesUtils;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Component
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ItemDao itemDao;


	/* 添加用户
	 * @param: [obj, pwd]
	 * @return: com.ynm3k.mvc.model.RespWrapper<java.lang.Long> */
	@Override
	public RespWrapper<Boolean> addUser(User user) {
		if (this.userDao.checkUserPhone(user.getPhone())) {
			return RespWrapper.makeResp(1001, "该手机账号已经存在!", null);
		}
		//封装数据
		// TODO
		Random random = new Random();
		int skey = random.nextInt();
		user.setSkey(skey);
		user.setStatus(AdminStatus.NORMAL.getValue());	// 状态 0 正常 10 禁用
		user.setCreateTime(System.currentTimeMillis());	// 创建时间,毫秒

		Boolean bln;
		try {
			bln = this.userDao.addUser(user);
		}catch (Exception e){
			log.info("添加User新用户失败:{}",e.getMessage());
			return RespWrapper.makeResp(1001, "添加系统账号失败："+e.getMessage(),false);
		}

		return RespWrapper.makeResp(0, "成功添加系统账号："+user.getPhone(), bln);
	}

	/* 更新用户
	 * @param: [user]
	 * @return: com.ynm3k.mvc.model.RespWrapper<java.lang.Boolean> */
	@Override
	public RespWrapper<Boolean> updateUser(User user) {
		//查询出要修改的实体bean
		RespWrapper<User> rObj = this.getOneUser(user.getUin());
		if (rObj.getErrCode() != 0 || rObj.getObj() == null) {
			log.info("更新user失败！，数据库中不存在此User账户 Uin:{}",user.getUin());
			return new RespWrapper(1002,"更新user失败！，数据库中不存在此User账户",null);
		}
		//封装参数 TODO
		//obj.setModifyBy(admin.getUin());		// 修改人
		User obj = rObj.getObj();
		//将user中的要修改参数拷贝到查询到的修改bean >> obj 中并且过滤掉user中属性值为null的属性
		BeanUtils.copyProperties(user, obj, CopyPropertiesUtils.getNullPropertyNames(user));
		obj.setModifyTime(System.currentTimeMillis());
		//保存更新
		Boolean bln = this.userDao.updateUser(obj);
		return RespWrapper.makeResp(0, "成功更新User账号："+user.getPhone(), bln);
	}

	@Override
	public RespWrapper<User> getUserBySid(String sid) {
		return null;
	}

	@Override
	public RespWrapper<User> getOneUser(long uin) {
		try {
			User user = new User().findById(uin);
			return RespWrapper.makeResp(0, "成功获取user账户 ：phone = "+user.getPhone(), user);
		}catch (Exception e){
			log.info("获取User账户失败：uin = {}",uin);
			return RespWrapper.makeResp(1001, "获取user账户失败：", null);
		}


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
		return RespWrapper.makeResp(0, "", userDao.getUserByPage(params, pageNo, pageSize));
	}

	@Override
	public RespWrapper<List<Item>> getItemBySelect(Admin admin) {
		try {
			List<Item>  itmes = this.itemDao.getItemList(admin);
			return RespWrapper.makeResp(0, "获取user下拉选项成功", itmes);
		}catch (Exception e){
			log.info("获取user下拉选项失败 admin={}",admin.toString());
			return RespWrapper.makeResp(1001, "获取user下拉选项失败：", null);
		}
	}

	@Override
	public RespWrapper<Map<Long, User>> getUserMapInIds(String userIds) {
		return RespWrapper.makeResp(0, "", this.userDao.getUserMapInIds(userIds));
	}

	@Override
	public RespWrapper<List<User>> getUserListInIds(String userIds) {
		try {
			List<User>  itmes = this.userDao.getUserListInIds(userIds);
			return RespWrapper.makeResp(0, "根据ids获取用户列表成功", itmes);
		}catch (Exception e){
			log.info("获取user下拉选项失败 userIds={}",userIds);
			return RespWrapper.makeResp(1001, "根据ids获取用户列表失败：", null);
		}
	}


}
