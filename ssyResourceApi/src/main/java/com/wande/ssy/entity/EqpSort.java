package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;

import javax.validation.constraints.NotNull;

public class EqpSort extends Model<EqpSort> {

    private Integer eqpsortId;        //'器材分类ID
    private Integer projectId;        //0无  1健身器材  2游乐设备  3笼式多功能球场(先保留字段), 4设施(公园内树木)
    private String eqpsortName;       //器材分类名称
    private Integer parentId;          //0 为父级,   上一级ID
    private String  path;             //多级分类路径
    private Integer level;            //等级
    private Long createTime ;         //创建时间
    private Long createBy ;        //创建人
    private Long modifyTime ;      //修改时间
    private Long modifyBy ;        //最后修改人

    @NotNull(message = "器材分类id不能为空！")
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

    @NotNull(message = "器材分类名称不能为空!")
    public String getEqpsortName() {
        return get("eqpsortName");
    }

    public void setEqpsortName(String eqpsortName) {
        set("eqpsortName", eqpsortName);
    }

    @NotNull(message = "上一级分类ID不能为空!")
    public Integer getParentId() {
        return get("parentId");
    }

    public void setParentId(Integer parentId) {
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

    public Long getCreateBy() {
        return get("createBy");
    }

    public void setCreateBy(Long createBy) {
        set("createBy", createBy);
    }

    public Long getModifyTime() {
        return get("modifyTime");
    }

    public void setModifyTime(Long modifyTime) {
        set("modifyTime", modifyTime);
    }

    public Long getModifyBy() {
        return get("modifyBy");
    }

    public void setModifyBy(Long modifyBy) {
        set("modifyBy", modifyBy);
    }

    //==================================================================================

    public String  getNamelabel() {
        return get("namelabel");
    }
}
