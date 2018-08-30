package com.wande.ssy.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.wande.ssy.entity.Log;

@Service(interfaceClass = IUserBussness.class)
public class UserBussnessImpl implements IUserBussness {

	public String getInfo(Integer userId) {
		Log l = new Log().findById(17);
		System.out.println(JSON.toJSONString(new Log().findById(17)));
		return "用户信息:" + userId;
	}

}
