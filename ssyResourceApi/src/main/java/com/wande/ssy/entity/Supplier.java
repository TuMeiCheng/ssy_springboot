package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;
import lombok.Data;

@Data
public class Supplier extends Model<Supplier> {

    private static final Long serialVersionUID = -3787690618147673790L;

    private Integer    supplierId;		// 主键
    private String csn;				// 供应商编号
    private String name;			// 供应商名称
    private String contactTel;		// 联系电话
    private String contactPerson;	// 联系人
    private Integer status;			// 状态 0启用 1禁用
    private Long   createTime;		// 创建时间
    private Integer   createBy;		// 创建人
    private Long   modifyTime;		// 修改时间
    private Integer   modifyBy;		// 最后修改人


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

    public String getName() {
       return get("name");

    }

    public void setName(String name) {
        set("name",name);

    }

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

    public Integer getCreateBy() {
        return get("createBy");
    }

    public void setCreateBy(Integer createBy) {
       set("createBy",createBy);
    }

    public Long getModifyTime() {
       return get("modifyTime");
    }

    public void setModifyTime(Long modifyTime) {
       set("modifyTime",modifyTime);
    }

    public Integer getModifyBy() {
       return get("modifyBy");
    }

    public void setModifyBy(Integer modifyBy) {
       set("modifyBy",modifyBy);
    }
}
