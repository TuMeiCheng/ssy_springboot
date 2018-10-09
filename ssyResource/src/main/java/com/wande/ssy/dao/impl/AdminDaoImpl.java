package com.wande.ssy.dao.impl;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wande.ssy.dao.AdminDao;
import com.wande.ssy.entity.Admin;
import com.wande.ssy.entity.AdminExt;
import com.ynm3k.mvc.model.DataPage;
import com.ynm3k.utils.crypto.MD5Coding;
import com.ynm3k.utils.string.StringUtil;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AdminDaoImpl implements AdminDao{


    /**
     * 根据ID获取一条数据
     * @param uin
     * @return
     */
    @Override
    public Admin getOneAdmin(Long uin) {
        try {
           Admin admin = new Admin().findById(uin);
          return admin;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*  添加admin用户
     * @param: [obj]
     * @return: java.lang.Boolean */
    @Override
    public Boolean addAdmin(Admin obj,String pwd) {
        return obj.save();
    }

    //判断账户是否存在
    @Override
    public boolean isExist(String account, long uin) {
        String sql = "SELECT * FROM eqp_admin where account='" + StringUtil.encodeSQL(account) + "'";
        if (uin != 0) {
            sql += " and uin !=" + uin;
        }
        Record record = Db.findFirst(sql);
        if (record != null) {
            return  true;
        }
        return false;
    }

    //根据account获取uin
    @Override
    public Long getUinByAccount(String account) {
        List<Record> list = Db.find("select * from eqp_admin where account = ?",account);
        if (list.size()>0){
            Record record = list.get(0);
            return record.get("uin");
        }
        return 0L;
    }

    /* 删除
     * @param: [uin]
     * @return: java.lang.Boolean */
    @Override
    public Boolean deleteAdmin(Long uin) {
        return new Admin().deleteById(uin);
    }

    /* 修改密码
     * @param: [obj, newPwd]
     * @return: java.lang.Boolean */
    @Override
    public Boolean updatePwd(Admin obj, String newPwd) {
        try {
            String pwd = MD5Coding.encode2HexStr(newPwd.getBytes("UTF-8"));
           return obj.findById(obj.getUin()).set("pwd",pwd).update();
            } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    /* 根据条件获取分页列表
     * @param: [params, pageNo, pageSize]
     * @return: com.ynm3k.mvc.model.DataPage */
    @Override
    public DataPage<Admin> getAdminByPage(Map params, Integer pageNo, Integer pageSize) {
        //定义参数
        int roleId = Integer.MAX_VALUE;	// 1管理员 2管理公司 3 体育局
        int orgId = Integer.MAX_VALUE;	// 体育局ID
        int status = Integer.MAX_VALUE;	// 状态 0 正常 10 禁用
        String keyword = "";	        //关键字

        //解析参数
        if (params != null) {
            if (params.containsKey("roleId")) {
                roleId = StringUtil.convertInt(params.get("roleId").toString(), Integer.MAX_VALUE);
            }
            if (params.containsKey("orgId")) {
                orgId = StringUtil.convertInt(params.get("orgId").toString(), Integer.MAX_VALUE);
            }
            if (params.containsKey("status")) {
                status = StringUtil.convertInt(params.get("status").toString(), Integer.MAX_VALUE);
            }
            if (params.containsKey("keyword")) {
                keyword = params.get("keyword").toString();
            }
        }
        String sql_select = "select * ";
        String sql = "from eqp_admin where 1=1";
        if (roleId != Integer.MAX_VALUE) {
            sql += " and roleId=" + roleId;
        }
        if (orgId != Integer.MAX_VALUE) {
            sql += " and orgId=" + orgId;
        }
        if (status != Integer.MAX_VALUE) {
            sql += " and status=" + status;
        }
        if (StringUtil.isNotEmpty(keyword)) {
            sql += " and ( account like '%" + StringUtil.encodeSQL(keyword) + "%' or name like '%" + StringUtil.encodeSQL(keyword) + "%')";
        }
        sql += " order by createTime desc";
        Page<Admin> adminPage = new Admin().paginate(pageNo,pageSize,sql_select,sql);
        System.out.println("pageSize:"+adminPage.getList().size());

        return new DataPage<Admin>(adminPage.getList(), adminPage.getTotalRow(), pageNo, pageSize);
    }

    /* 更新Admin
     * @param: [admin]
     * @return: java.lang.Boolean */
    @Override
    public Boolean updateAdmin(Admin admin) {
        return admin.update();
    }

    /**
     * 根据adminIds 获取admin列表, 用于优化查询数据, 少查SQL
     * @param adminIds
     * @return
     */
    @Override
    public Map<Long, Admin> getAdminMapInIds(String adminIds) {

        adminIds = StringUtil.isEmpty(adminIds) ? "''" : adminIds;
        Map<Long, Admin> list = new HashMap<Long, Admin>();
        //查询数据
        String sql="select * from eqp_admin where uin in(" + adminIds + ")";
        try {
            List<Admin> list2 = new Admin().find(sql);
            for (Admin obj : list2) {
                list.put(obj.getUin(),obj);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<Long, Admin>();

    }

    /**
     * 根据account获取一条数据
     * @param account
     * @return
     */
    @Override
    public AdminExt getOneAdminExt(String account) {

        String sql = "select * from eqp_admin where account='" + StringUtil.encodeSQL(account) +"'";
        Record record = Db.findFirst(sql);
        if (record != null) {
            AdminExt obj = parseToAdminExt(record);
            return obj;
        }
        return null;
    }

    /**
     * 更新系统用户最后登录时间
     * @return
     */
    @Override
    public Boolean updateLastLoginTime(@NotNull(message = "uin不能为空！") Long uin) {

        String sql = "update eqp_admin set loginTime=" +System.currentTimeMillis()+ " where uin="+uin;

        try {
            int rs = Db.update(sql);
            if(rs > 0){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }


    /**
     * DB结果集转实体类AdminExt
     * @return
     * @throws SQLException
     */
    public AdminExt parseToAdminExt(Record rs){
        AdminExt obj = new AdminExt();
        obj.setUin(rs.getLong("uin"));
        obj.setRoleId(rs.getInt("roleId"));
        obj.setOrgId(rs.getInt("orgId"));
        obj.setAccount(rs.getStr("account"));
        obj.setPwd(rs.getStr("pwd"));
        obj.setSkey(rs.getInt("skey"));
        obj.setName(rs.getStr("name"));
        obj.setEmail(rs.getStr("email"));
        obj.setPhone(rs.getStr("phone"));
        obj.setStatus(rs.getInt("status"));
        obj.setLoginTime(rs.getLong("loginTime"));
        obj.setLoginIp(rs.getStr("loginIp"));
        obj.setCreateTime(rs.getLong("createTime"));
        obj.setCreateBy(rs.getLong("createBy"));
        obj.setModifyTime(rs.getLong("modifyTime"));
        obj.setModifyBy(rs.getLong("modifyBy"));
        return obj;
    }



}
