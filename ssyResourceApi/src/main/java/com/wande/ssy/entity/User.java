package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class User extends Model<User>{
    private static final long serialVersionUID = 2412053097856391192L;

    private long   uin;			// userId
    private long   agencyId;	// 管理公司ID
    private String phone;		// APP用户手机号
    private String password;     //密码
    private  Integer skey;      //随机因子
    private String name;		// 用户名称
    private String img;			// 头像
    private int    status;		// 状态 0可用  10冻结 20删除
    private long   loginTime;	// 登录时间
    private long   createTime;	// 创建时间
    private long   createBy;	// 创建人
    private long   modifyTime;	// 修改时间
    private long   modifyBy;	// 最后修改人

    //====================================================================================================



    public long getUin() {
        return get("uin");
    }

    public void setUin(long uin) {
        set("uin",uin);
    }

    public long getAgencyId() {
        return get("agencyId");
    }

    public void setAgencyId(long agencyId) {
        set("agencyId",agencyId);
    }

    @NotEmpty(message = "手机号码必填")
    @Pattern(regexp = "^(((13[0-9]{1})|(15[0-9]{1})|(17[6-8]{1})|(14[5-7]{1})|(18[0-9]{1}))+\\d{8})$"
            ,message = "请输入正确的手机号！")
    public String getPhone() {
        return get("phone");
    }

    public void setPhone(String phone) {
        set("phone",phone);
    }

    @NotBlank(message = "密码必填")
    @Size(min=6, max=16,message = "必须输入长度在6-16之间的密码!")
    public String getPassword() {
        return get("pwd");
    }

    public void setPassword(String pwd) {
        set("pwd",pwd);
    }


    public Integer getSkey() {
        return get("skey");
    }

    public void setSkey(Integer skey) {
        set("skey",skey);
    }

    @NotEmpty(message = "名称必填")
    public String getName() {
        return get("name");
    }

    public void setName(String name) {
        set("name",name);
    }

    public String getImg() {
        return get("img");
    }

    public void setImg(String img) {
        set("img",img);
    }

    public int getStatus() {
        return get("status");
    }

    public void setStatus(int status) {
        set("status",status);
    }

    public long getLoginTime() {
        return get("loginTime");
    }

    public void setLoginTime(long loginTime) {
        set("loginTime",loginTime);
    }

    public long getCreateTime() {
        return get("createTime");
    }

    public void setCreateTime(long createTime) {
        set("createTime",createTime);
    }

    public long getCreateBy() {
        return get("createBy");
    }

    public void setCreateBy(long createBy) {
        set("createBy",createBy);
    }

    public long getModifyTime() {
        return get("modifyTime");
    }

    public void setModifyTime(long modifyTime) {
        set("modifyTime",modifyTime);
    }

    public long getModifyBy() {
        return get("modifyBy");
    }

    public void setModifyBy(long modifyBy) {
        set("modifyBy",modifyBy);
    }
}
