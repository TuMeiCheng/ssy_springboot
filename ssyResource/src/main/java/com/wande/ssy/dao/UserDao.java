package com.wande.ssy.dao;

import com.wande.ssy.entity.User;
import com.ynm3k.mvc.model.DataPage;

import java.util.List;
import java.util.Map;

public interface UserDao  {

    //添加user
    Boolean addUser(User user);

    //更新User
    Boolean updateUser(User user);

    //检查该手机是否注册过
    Boolean checkUserPhone(String phone);


     //* 根据userIds获取user列表

    List<User> getUserListInIds(String userIds);

    /**
     * 分页多条件查询
     * @param params
     * @param pageNo
     * @param pageSize
     * @return
     */
    DataPage<User> getUserByPage(Map<String, Object> params, Integer pageNo, Integer pageSize);
}
