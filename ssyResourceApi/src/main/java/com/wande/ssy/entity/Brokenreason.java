package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;

public class Brokenreason extends Model<Brokenreason> {

    private Integer reasonId;		// 报修原因主键
    private Integer parentId;		// 父级ID
    private String name;			// 原因名称
    private Long createTime;	    // 创建时间
    private Long createBy;		// 创建人
    private Long modifyTime;	    // 修改时间
    private Long modifyBy;		// 最后修改人

    public Integer getReasonId() {
        return getInt("reasonId");
    }

    public void setReasonId(Integer reasonId) {
        set("reasonId", reasonId);
    }

    public Integer getParentId() {
        return getInt("parentId");
    }

    public void setParentId(Integer parentId) {
        set("parentId", parentId);
    }

    public String getName() {
        return getStr("name");
    }

    public void setName(String name) {
        set("name", name);
    }
    public Long getCreateTime() {
        return getLong("createTime");
    }

    public void setCreateTime(Long createTime) {
        set("createTime", createTime);
    }

    public Long getCreateBy() {
        return getLong("createBy");
    }

    public void setCreateBy(Long createBy) {
        set("createBy", createBy);
    }

    public Long getModifyTime() {
        return getLong("modifyTime");
    }

    public void setModifyTime(Long modifyTime) {
        set("modifyTime", modifyTime);
    }

    public Long getModifyBy() {
        return getLong("modifyBy");
    }

    public void setModifyBy(Long modifyBy) {
        set("modifyBy", modifyBy);
    }
}
