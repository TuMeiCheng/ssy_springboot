package com.wande.ssy.dao.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.wande.ssy.dao.UserDao;
import com.wande.ssy.entity.User;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.utils.string.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {


    /* 添加 user
     * @param: [user]
     * @return: java.lang.Boolean */
    @Override
    public Boolean addUser(User user) {
        return user.save();
    }

    /* 更新 user
     * @param: [user]
     * @return: java.lang.Boolean */
    @Override
    public Boolean updateUser(User user) {
        return user.update();
    }

    /* 检查手机是否注册过
     * @param: [phone]
     * @return: java.lang.Boolean */
    @Override
    public Boolean checkUserPhone(String phone) {
        User user = new User().findFirst("SELECT * FROM eqp_user where phone = ?", phone);
        if (user != null) {
            return true;
        }
        return false;
    }

    /**
     * 根据userIds获取user列表
     *
     * @param userIds
     * @return
     */
    @Override
    public List<User> getUserListInIds(String userIds) {

        userIds = StringUtil.isEmpty(userIds) ? "''" : userIds;
        //查询数据
        String sql = "select * from eqp_user where uin in (" + userIds + ")";
        try {
            User user = new User();
            List<User> users = user.find(sql);
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<User>();

    }

    /**
     * 分页多条件查询
     * @param params
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public DataPage<User> getUserByPage(Map<String, Object> params, Integer pageNo, Integer pageSize) {
        List<User> list = new ArrayList<User>();
        //定义参数
        long   agencyId = Long.MAX_VALUE;		// 管理公司ID
        String keyword = "";	//名称或者手机
        //解析参数
        if (params != null) {
            if (params.containsKey("agencyId")) {
                agencyId = StringUtil.convertLong(params.get("agencyId").toString(), Long.MAX_VALUE);
            }
            if(params.containsKey("keyword")){
                keyword = params.get("keyword").toString();
            }
        }
        //查询数据
        String sql_select = "select * ";
        String sql = "from eqp_user where 1=1";
        if (agencyId != Long.MAX_VALUE) {
            sql += " and agencyId=" + agencyId;
        }
        if(StringUtil.isNotEmpty(keyword)){
            sql += " and ( name like '%" + StringUtil.encodeSQL(keyword) + "%' or phone like '%" + StringUtil.encodeSQL(keyword) + "%' )";
        }
        sql += " order by createTime desc";

        Db.paginate(pageNo, pageSize, sql_select, sql);
        Page<User> user_page = new User().paginate(pageNo,pageSize,sql_select,sql);

        return new DataPage<User>(user_page.getList(), user_page.getTotalRow(), pageNo, pageSize);

    }

}
