package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;

public class Region extends Model<Region> {

    private static final Long serialVersionUID = 948576853211897059L;

    private Integer    regionId;	// 地区ID
    private Integer    parentId;	// 上一级地区ID
    private Integer    level;		// 1,2,3,4表示省市区场地
    private String scode;		// 拼音简称
    private String name;		// 地区名称
    private String path;		// path路径
    private Long   createTime;	// 创建时间
    private Integer   createBy;	// 创建人
    private Long   modifyTime;	// 修改时间
    private Long   modifyBy;	// 最后修改时间


    public Integer getRegionId() {
        return get("regionId");
    }

    public void setRegionId(Integer regionId) {
       set("regionId",regionId);
    }

    public Integer getParentId() {
        return get("parentId");
    }

    public void setParentId(Integer parentId) {
       set("parentId",parentId);
    }

    public Integer getLevel() {
        return get("level");
    }

    public void setLevel(Integer level) {
      set("level",level);
    }

    public String getScode() {
       return get("scode");
    }

    public void setScode(String scode) {
       set("scode",scode);
    }

    public String getName() {
        return get("name");
    }


    public void setName(String name) {
       set("name",name);
    }

    public String getPath() {
        return get("path");
    }

    public void setPath(String path) {
        set("path",path);
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

    public Long getModifyBy() {
       return get("modifyBy");
    }

    public void setModifyBy(Long modifyBy) {
      set("modifyBy",modifyBy);
    }
}
