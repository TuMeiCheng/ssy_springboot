package com.wande.ssy.dao.impl;

import com.wande.ssy.dao.UserDao;
import com.wande.ssy.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {


    /* 添加 user
     * @param: [user]
     * @return: java.lang.Boolean */
    @Override
    public Boolean addUser(User user) {
        return  user.save();
    }

    /* 更新 user
     * @param: [user]
     * @return: java.lang.Boolean */
    @Override
    public Boolean updateUser(User user) {
        return  user.update();
    }

    /* 检查手机是否注册过
     * @param: [phone]
     * @return: java.lang.Boolean */
    @Override
    public Boolean checkUserPhone(String phone) {
        User user = new User().findFirst("SELECT * FROM eqp_user where phone = ?",phone);
        if (user != null) {
            return true;
        }
        return false;
    }
}
