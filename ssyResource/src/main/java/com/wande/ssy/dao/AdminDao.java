package com.wande.ssy.dao;

import com.wande.ssy.entity.Admin;
import com.ynm3k.mvc.model.DataPage;

import java.util.Map;

public interface AdminDao {

    /**
     * 根据ID获取一条数据
     * @param uin
     * @return
     */
    public Admin getOneAdmin(Long uin);

    //添加admin用户
    Boolean addAdmin(Admin obj,String pwd);

    //判读账号是否已经存在
    boolean isExist(String account, long uin);

    //根据account获取uin
    Long getUinByAccount(String  account);

    //删除
    Boolean deleteAdmin(Long uin);

    //修改密码
    Boolean updatePwd(Admin obj, String newPwd);

    //根据条件获取分页列表
    DataPage<Admin> getAdminByPage(Map params, Integer pageNo, Integer pageSize);

    //更新Admin
    Boolean updateAdmin(Admin admin);


}
