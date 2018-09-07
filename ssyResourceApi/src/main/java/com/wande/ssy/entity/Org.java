package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;

public class Org extends Model<Org> {

    private static final Long serialVersionUID = -1980930091536923073L;

    private Integer    orgId;		// 管辖机构ID
    private Integer    parentId;	// 上级ID
    private String path;		// path路径
    private Integer    regionId;	// 地区ID
    private String name;		// 管辖机构名称
    private Integer    level;		// level
    private Long   createTime;	// 创建时间
    private Integer   createBy;	// 创建人
    private Long   modifyTime;	// 修改时间
    private Integer   modifyBy;	// 最后修改人


    public Integer getOrgId() {
       return get("orgId");

    }

    public void setOrgId(Integer orgId) {
       set("orgId",orgId);
       
    }

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

    public Integer getRegionId() {
       return get("regionId");

    }

    public void setRegionId(Integer regionId) {
       set("regionId",regionId);
       
    }

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

    public Integer getModifyBy(){
       return get("modifyBy");

    }

    public void setModifyBy(Integer modifyBy) {
       set("modifyBy",modifyBy);
       
    }
}
