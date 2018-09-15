package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class User extends Model<User>{
    private static final Long serialVersionUID = 2412053097856391192L;

    private Long   uin;			// userId
    private Integer   agencyId;	// 管理公司ID
    private String phone;		// APP用户手机号
    private String password;     //密码
    private Integer skey;      //随机因子
    private String name;		// 用户名称
    private String img;			// 头像
    private Integer    status;		// 状态 0可用  10冻结 20删除
    private Integer   loginTime;	// 登录时间
    private Long   createTime;	// 创建时间
    private Long   createBy;	// 创建人
    private Long   modifyTime;	// 修改时间
    private Integer   modifyBy;	// 最后修改人

    //====================================================================================================


    @NotNull(message = "uin不能为空")
    public Long getUin() {
        return get("uin");
    }

    public void setUin(Long uin) {
        set("uin",uin);
    }

    public Long getAgencyId() {
        return get("agencyId");
    }

    public void setAgencyId(Integer agencyId) {
        set("agencyId",agencyId);
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

    @NotNull(message = "状态不能为空")
    public Integer getStatus() {
        return get("status");
    }

    public void setStatus(Integer status) {
        set("status",status);
    }

    public Long getLoginTime() {
        return get("loginTime");
    }

    public void setLoginTime(Long loginTime) {
        set("loginTime",loginTime);
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

    public void setCreateBy(Long  createBy) {
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

    public String getAgencyName(){
        return get("agencyName");
    }
}
