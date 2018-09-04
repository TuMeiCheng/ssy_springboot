package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;

public class EqpSort extends Model<EqpSort> {

    private Integer eqpsortId;        //'器材分类ID
    private Integer projectId;        //0无  1健身器材  2游乐设备  3笼式多功能球场(先保留字段), 4设施(公园内树木)
    private String eqpsortName;       //器材分类名称
    private String parentId;          //0,为父级,   上一级ID
    private String  path;             //'多级分类路径
    private Integer level;            //等级
    private Long createTime ;         //创建时间
    private Integer createBy ;        //创建人
    private Integer modifyTime ;      //修改时间
    private Integer modifyBy ;        //最后修改人

    public Integer getEqpsortId() {
        return get("eqpsortId");
    }

    public void setEqpsortId(Integer eqpsortId) {
        set("eqpsortId",eqpsortId);
    }

    public Integer getProjectId() {
        return get("projectId");
    }

    public void setProjectId(Integer projectId) {
        set("projectId", projectId);
    }

    public String getEqpsortName() {
        return get("eqpsortName");
    }

    public void setEqpsortName(String eqpsortName) {
        set("eqpsortName", eqpsortName);
    }

    public String getParentId() {
        return get("parentId");
    }

    public void setParentId(String parentId) {
        set("parentId", parentId);
    }

    public String getPath() {
        return get("path");
    }

    public void setPath(String path) {
        set("path", path);
    }

    public Integer getLevel() {
        return get("level");
    }

    public void setLevel(Integer level) {
        set("level", level);
    }

    public Long getCreateTime() {
        return get("createTime");
    }

    public void setCreateTime(Long createTime) {
        set("createTime", createTime);
    }

    public Integer getCreateBy() {
        return get("createBy");
    }

    public void setCreateBy(Integer createBy) {
        set("createBy", createBy);
    }

    public Integer getModifyTime() {
        return get("modifyTime");
    }

    public void setModifyTime(Integer modifyTime) {
        set("modifyTime", modifyTime);
    }

    public Integer getModifyBy() {
        return get("modifyBy");
    }

    public void setModifyBy(Integer modifyBy) {
        set("modifyBy", modifyBy);
    }
}
