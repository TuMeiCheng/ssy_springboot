package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Admin extends Model<Admin> {

    private static final Long serialVersionUID = -389434528308632959L;

    private Long   uin;			// 系统用户ID
    private Long    roleId;		// 1管理员 2管理公司 3 体育局
    private Long    orgId;		// 体育局ID
    private String account;		// 登录账号
    private String name;		// 姓名
    private String email;		// 邮箱
    private String phone;		// 电话
    private Long    status;		// 状态 0 正常 10 禁用
    private Long   logLongime;	// 上一次登录时间
    private String loginIp;		// 最后登录IP
    private Long   createTime;	// 创建时间
    private Long   createBy;	// 创建人
    private Long   modifyTime;	// 修改时间
    private Long   modifyBy;	// 最后修改人

    private String  password;   //密码
    private int skey;        //随机因子

    private String  roleName;    //角色名称
    private String  regionIds;   //地区ids

    public String getRegionIds() {
        return get("regionIds");
    }

    public String getRoleName() {
        return get("roleName");
    }

    public int getSkey() {
       return get("skey");
    }

    public void setSkey(int skey) {
        set("skey",skey);
    }

    @NotBlank(message = "密码必填")
    @Size(min=6, max=16,message = "必须输入长度在6-16之间的密码!")
    public String getPassword() {
       return get("pwd");
    }

    public void setPassword(String pwd) {
       set("pwd",pwd);
    }

    @NotNull(message = "uin不能为空！")
    public Long getUin() {
        return get("uin");
    }

    public void setUin(Long uin) {
        set("uin",uin);
    }

    public Integer getRoleId() {
        return get("roleId");
    }

    public void setRoleId(Integer roleId) {
       set("roleId",roleId);
    }

    @NotNull(message = "管辖机构id不能为空")
    public Integer getOrgId() {
      return get("orgId");
    }

    public void setOrgId(Integer orgId) {
        set("orgId",orgId);
    }

    @NotNull(message = "账号不能为空")
    public String getAccount() {
       return get("account");
    }

    public void setAccount(String account) {
        set("account",account);
    }

    @NotNull(message = "名称不能为空!")
    public String getName() {
        return get("name");
    }

    public void setName(String name) {
        set("name",name);
    }
    @NotNull(message = "邮箱必填")
    @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"
        ,message = "请输入正确的邮箱地址！")
    public String getEmail() {
        return get("email");
    }

    public void setEmail(String email) {
        set("email",email);
    }

    @NotNull(message = "手机号码必填")
    @Pattern(regexp = "^(((13[0-9]{1})|(15[0-9]{1})|(17[6-8]{1})|(14[5-7]{1})|(18[0-9]{1}))+\\d{8})$"
            ,message = "请输入正确的手机号！")
    public String getPhone() {
        return get("phone");
    }

    public void setPhone(String phone) {
        set("phone",phone);
    }

    @NotNull(message = "状态不能为空！")
    public Integer getStatus() {
        return get("status");
    }

    public void setStatus(Integer status) {
        set("status",status);
    }

    public Long getLogLongime() {
        return get("logLongime");
    }

    public void setLogLongime(Long logLongime) {
        set("logLongime",logLongime);
    }

    public String getLoginIp() {
        return get("loginIp");
    }

    public void setLoginIp(String loginIp) {
       set("loginIp",loginIp);
    }

    public Long getCreateTime() {
       return get("createTime");
    }

    public void setCreateTime(Long createTime) {
       set("createTime",createTime);
    }

    public Long getCreateBy() {
       return get("createBy");
    }

    public void setCreateBy(Long createBy) {
        set("createBy",createBy);
    }


    public Long getModifyTime() {
       return get("modifyTime");
    }

    public void setModifyTime(Long modifyTime) {
       set("modifyTime",modifyTime);
    }

    public Long getModifyBy() {
       return get("modifyBy");
    }

    public void setModifyBy(Long modifyBy) {
       set("modifyBy",modifyBy);
    }
}
