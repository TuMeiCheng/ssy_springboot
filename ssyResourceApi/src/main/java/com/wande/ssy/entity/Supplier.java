package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class Supplier extends Model<Supplier> {

    private static final Long serialVersionUID = -3787690618147673790L;

    private Integer supplierId;		// 主键
    private String csn;				// 供应商编号
    private String name;			// 供应商名称
    private String contactTel;		// 联系电话
    private String contactPerson;	// 联系人
    private Integer status;			// 状态 0启用 1禁用
    private Long   createTime;		// 创建时间
    private Integer   createBy;		// 创建人
    private Long   modifyTime;		// 修改时间
    private Integer   modifyBy;		// 最后修改人


    @NotNull(message = "供应商ID不能为空!")
    public Integer getSupplierId() {
       return get("supplierId");

    }

    public void setSupplierId(Integer supplierId) {
       set("supplierId",supplierId);

    }

    public String getCsn() {
        return get("csn");

    }

    public void setCsn(String csn) {
       set("csn",csn);

    }

    @NotNull(message = "供应商名称不能为空!")
    public String getName() {
       return get("name");

    }

    public void setName(String name) {
        set("name",name);

    }

    @NotNull(message = "请输入正确的手机号码!")
    @Pattern(regexp = "^(((13[0-9]{1})|(15[0-9]{1})|(17[6-8]{1})|(14[5-7]{1})|(18[0-9]{1}))+\\d{8})$"
            ,message = "请输入正确的手机号！")
    public String getContactTel() {
       return get("contactTel");

    }

    public void setContactTel(String contactTel) {
      set("contactTel",contactTel);

    }

    public String getContactPerson() {
        return get("contactPerson");
    }

    public void setContactPerson(String contactPerson) {
      set("contactPerson",contactPerson);
    }

    @NotNull(message = "状态不能为空!")
    public Integer getStatus() {
        return get("status");
    }

    public void setStatus(Integer status) {
        set("status",status);
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


    //================================================================================
    public String  getCreateName() {
        return get("createName");
    }
}
