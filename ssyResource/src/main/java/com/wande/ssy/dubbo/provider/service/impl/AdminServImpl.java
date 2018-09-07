package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.AdminDao;
import com.wande.ssy.dubbo.provider.service.AdminService;
import com.wande.ssy.entity.Admin;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Service(interfaceClass = AdminService.class)
public class AdminServImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;



    @Override
    public RespWrapper<Long> addAdmin(Admin obj, String pwd, String regionIds) {
        return null;
    }

    @Override
    public RespWrapper<Boolean> updateAdmin(Admin obj, String regionIds) {
        return null;
    }

    @Override
    public RespWrapper<Admin> getAdminBySid(String sid) {
        return null;
    }

    @Override
    public RespWrapper<Admin> getAdminByUin(long uin) {
        return null;
    }

    @Override
    public RespWrapper<String> login(String account, String password, String vCode) {
        return null;
    }

    @Override
    public RespWrapper<Boolean> checkAccount(String account) {
        return null;
    }

    @Override
    public RespWrapper<Boolean> resetPwd(String account, String password) {
        return null;
    }

    @Override
    public RespWrapper<Boolean> changePwd(String account, String oldPwd, String newPwd) {
        return null;
    }

    @Override
    public RespWrapper<DataPage<Admin>> getAdminByPage(Map<String, Object> params, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public RespWrapper<Map<Long, Admin>> getAdminMapInIds(String adminIds) {
        return null;
    }
}
