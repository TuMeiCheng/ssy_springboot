package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;

import javax.validation.constraints.NotNull;

public class Org extends Model<Org> {

    private static final Long serialVersionUID = -1980930091536923073L;

    private Integer    orgId;		// 管辖机构ID
    private Integer    parentId;	// 上级ID
    private String path;		// path路径
    private Integer    regionId;	// 地区ID
    private String name;		// 管辖机构名称
    private Integer    level;		// level
    private Long   createTime;	// 创建时间
    private Long   createBy;	// 创建人
    private Long   modifyTime;	// 修改时间
    private Long   modifyBy;	// 最后修改人

    @NotNull(message = "管辖机构id不能为空！")
    public Integer getOrgId() {
       return get("orgId");
    }

    public void setOrgId(Integer orgId) {
       set("orgId",orgId);
       
    }

    @NotNull(message = "父级地区ID不能为空!")
    public Integer getParentId() {
       return get("parentId");

    }

    public void setParentId(Integer parentId) {
       set("parentId",parentId);
       
    }

    public String getPath() {
        return get("path");

    }

    public void setPath(String path) {
       set("path",path);
       
    }

    @NotNull(message = "所属区域ID不能为空!")
    public Integer getRegionId() {
       return get("regionId");

    }

    public void setRegionId(Integer regionId) {
       set("regionId",regionId);
       
    }

    @NotNull(message = "管辖机构名称不能为空!")
    public String getName() {
       return get("name");

    }

    public void setName(String name) {
        set("name",name);
        
    }

    public Integer getLevel() {
       return get("level");

    }

    public void setLevel(Integer level) {
       set("level",level);
       
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

    public Long getModifyBy(){
       return get("modifyBy");
    }

    public void setModifyBy(Long modifyBy) {
       set("modifyBy",modifyBy);
    }

    //===================================================================
    public String  getNamelabel(){
        return get("namelabel");
    }
}
