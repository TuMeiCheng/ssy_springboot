package com.wande.ssy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wande.ssy.dao.AdminDao;
import com.wande.ssy.dao.RegionagencyDao;
import com.wande.ssy.dubbo.provider.service.AdminService;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.AdminExt;
import com.wande.ssy.entity.RegionAgency;
import com.wande.ssy.enums.AdminRole;
import com.wande.ssy.enums.AdminStatus;
import com.wande.ssy.utils.CopyPropertiesUtils;
import com.wande.ssy.utils.SidUtil;
import com.wande.ssy.utils.StringUtil;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.mvc.model.RespWrapper;
import com.ynm3k.utils.crypto.MD5Coding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Random;

@Slf4j
@Service(interfaceClass = AdminService.class)
public class AdminServImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private RegionagencyDao regionagencyDao;
    


    //添加admin系统用户
    @Override
    public RespWrapper<Long> addAdmin(Admin obj, String pwd, String regionIds) {
        if(adminDao.isExist(obj.getAccount(), 0)){
            return RespWrapper.makeResp(1001, "该账号已经存在!", null);
        }
        Random random = new Random();
        int skey = random.nextInt();
        obj.setSkey(skey);                                  //随机值
        obj.setStatus(AdminStatus.NORMAL.getValue());	    // 状态 0 正常 10 禁用
        obj.setCreateTime(System.currentTimeMillis());	    // 创建时间,毫秒

        try {
            String password = MD5Coding.encode2HexStr(pwd.getBytes("UTF-8"));
            obj.setPassword(password);
        }catch (Exception e){
            e.printStackTrace();
        }
        Boolean bln = this.adminDao.addAdmin(obj,obj.getPassword());
        if (bln) {
            //如果是管理公司账号,添加对应的cregionagency记录
            Long agencyId = this.adminDao.getUinByAccount(obj.getAccount());
            if (obj.getRoleId() == AdminRole.AGENCY.getValue()) {
                RegionAgency cAgency = new RegionAgency();
                cAgency.setRegionIds(regionIds);
                cAgency.setAgencyId(agencyId);
                boolean crs = regionagencyDao.insert(cAgency);
                if (!crs) {
                    adminDao.deleteAdmin(agencyId);
                }
            }
            return RespWrapper.makeResp(0, "添加成功", agencyId);
        }
        return RespWrapper.makeResp(1001, "系统繁忙!", 0L);
    }

    @Override
    public RespWrapper<Boolean> updateAdmin(Admin obj, String regionIds) {
        Admin admin = this.adminDao.getOneAdmin(obj.getUin());
        if (admin == null) {
            log.info("更新admin失败！，数据库中不存在此Admin账户 Uin:{}",obj.getUin());
            return new RespWrapper(1002,"更新admin失败！，数据库中不存在此Admin账户",null);
        }

        //将obj中的要修改参数拷贝到查询到的修改bean >> admin 中并且过滤掉obj中属性值为null的属性
        BeanUtils.copyProperties(obj, admin, CopyPropertiesUtils.getNullPropertyNames(obj));
        admin.setModifyTime(System.currentTimeMillis());
        //保存更新
        Boolean bln = this.adminDao.updateAdmin(admin);

        if (bln) {
            //如果是管理公司账号,添加对应的cregionagency记录
            if (admin.getRoleId() == AdminRole.AGENCY.getValue()) {
                RegionAgency cAgency = regionagencyDao.getOneRegionagency(admin.getUin());
                if (cAgency == null) {
                    log.info("AdminServiceImpl:更新cregionAgency出错:Id为:{} ,添加的regionIds:{}",admin.getUin(),regionIds);
                }
                cAgency.setRegionIds(regionIds);
                boolean crs = regionagencyDao.update(cAgency);
                if (!crs) {
                    log.info("AdminServiceImpl:更新cregionAgency出错:Id为:{} ,添加的regionIds:{}",admin.getUin(),regionIds);
                }
            }
            return RespWrapper.makeResp(0, "", true);
        } else {
            return RespWrapper.makeResp(1001, "系统繁忙!", false);
        }

    }

    @Override
    public RespWrapper<Admin> getAdminBySid(String sid) {

        if (StringUtil.isEmpty(sid) || sid.length() != 48) {
            return RespWrapper.makeResp(1001, "sid不正确!", null);
        }
        try {
            SidUtil.SidInfo info = SidUtil.decodeSid(sid);
            Admin admin = adminDao.getOneAdmin(info.uin);
            if (admin != null) {
                return RespWrapper.makeResp(0, "", admin);
            } else {
                return RespWrapper.makeResp(1001, "该系统用户不存在!", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespWrapper.makeResp(1001, "系统繁忙!", null);

    }

    /* 根据uin获取用户
     * @param: [uin]
     * @return: com.ynm3k.mvc.model.RespWrapper<com.wande.ssy.entity.Admin> */
    @Override
    public RespWrapper<Admin> getAdminByUin(long uin) {
        try {
            Admin admin = adminDao.getOneAdmin(uin);
            if (admin != null) {
                return RespWrapper.makeResp(0, "", admin);
            } else {
                return RespWrapper.makeResp(1001, "该系统用户不存在!", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespWrapper.makeResp(1001, "系统繁忙!", null);
    }

    @Override
    public RespWrapper<String> login(String account, String password, String vCode) {

        try {
            AdminExt admin = adminDao.getOneAdminExt(account);
            if (admin == null) {
                return RespWrapper.makeResp(1001, "该系统用户不存在!", null);
            }
            String pwd = admin.getPwd();
            if (!pwd.equals(MD5Coding.encode2HexStr(password.getBytes("UTF-8")))) {
                return RespWrapper.makeResp(1001, "用户密码错误!", null);
            }
            if (admin.getStatus() != AdminStatus.NORMAL.getValue()) {
                return RespWrapper.makeResp(1001, "该账号已被冻结!", null);
            }
            adminDao.updateLastLoginTime(admin.getUin());
            String sid = SidUtil.createSid(admin.getUin(), admin.getSkey(), System.currentTimeMillis());
            return RespWrapper.makeResp(0, "登录成功", sid);
        } catch (Exception e) {
            return RespWrapper.makeResp(1001, "系统繁忙!", null);
        }

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
        Long uin =adminDao.getUinByAccount(account);
        if (uin == null || uin == 0){
            return RespWrapper.makeResp(1001, "该系统用户不存在!", false);
        }
        Admin admin = this.adminDao.getOneAdmin(uin);
        try {
            String oldPwdHex = MD5Coding.encode2HexStr(oldPwd.getBytes("UTF-8"));
            if (!admin.getPassword().equals(oldPwdHex)) {
                return RespWrapper.makeResp(1001, "旧密码输入错误,请重试!", false);
            }
           Boolean rs = adminDao.updatePwd(admin,newPwd);
            if (rs) {
                return RespWrapper.makeResp(0, "修改密码成功!", true);
            } else {
                return RespWrapper.makeResp(1001, "系统繁忙!", false);
            }
        } catch (Exception e) {
            return RespWrapper.makeResp(1001, "系统繁忙!", false);
        }
    }

    /*
    //根据条件获取分页列表
     * @param: [params, pageNo, pageSize]
     * @return: com.ynm3k.mvc.model.RespWrapper<com.ynm3k.mvc.model.DataPage<com.wande.ssy.entity.Admin>> */
    @Override
    public RespWrapper<DataPage<Admin>> getAdminByPage(Map<String, Object> params, int pageNo, int pageSize) {
        DataPage<Admin> page = adminDao.getAdminByPage(params, pageNo, pageSize);
        return RespWrapper.makeResp(0, "", page);
    }

    @Override
    public RespWrapper<Map<Long, Admin>> getAdminMapInIds(String adminIds) {
        return RespWrapper.makeResp(0, "", this.adminDao.getAdminMapInIds(adminIds));
    }
}
