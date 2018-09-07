package com.wande.ssy.dao;

import com.wande.ssy.entity.User;

public interface UserDao  {

    //添加user
    Boolean addUser(User user);

    //更新User
    Boolean updateUser(User user);

    //检查该手机是否注册过
    Boolean checkUserPhone(String phone);
}
