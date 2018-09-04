package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;

public class Brokenreason extends Model<Brokenreason> {



    private static final long serialVersionUID = 947923646748266127L;
    public static final Brokenreason dao = new Brokenreason();

    private String reasonId   = "reasonId";		// 报修原因主键
    private String parentId   = "parentId";		// 父级ID
    private String name       = "name";			// 原因名称
    private String createTime = "createTime";	// 创建时间
    private String createBy   = "createBy";		// 创建人
    private String modifyTime = "modifyTime";	// 修改时间
    private String modifyBy   = "modifyBy";		// 最后修改人

    public Integer getReasonId() {
        return getInt(reasonId);
    }

    public void setReasonId(int reasonId) {
        set(this.reasonId, reasonId);
    }

    public Integer getParentId() {
        return getInt(parentId);
    }

    public void setParentId(int parentId) {
        set(this.parentId, parentId);
    }

    public String getName() {
        return getStr(name);
    }

    public void setName(String name) {
        set(this.name, name);
    }
    public Long getCreateTime() {
        return getLong(createTime);
    }

    public void setCreateTime(long createTime) {
        set(this.createTime, createTime);
    }

    public Long getCreateBy() {
        return getLong(createBy);
    }

    public void setCreateBy(long createBy) {
        set(this.createBy, createBy);
    }

    public Long getModifyTime() {
        return getLong(modifyTime);
    }

    public void setModifyTime(long modifyTime) {
        set(this.modifyTime, modifyTime);
    }

    public Long getModifyBy() {
        return getLong(modifyBy);
    }

    public void setModifyBy(long modifyBy) {
        set(this.modifyBy, modifyBy);
    }
}
