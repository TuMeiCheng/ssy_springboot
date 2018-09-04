package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;
import lombok.Data;

@Data
public class Region extends Model<Region> {

    private static final long serialVersionUID = 948576853211897059L;

    private int    regionId;	// 地区ID
    private int    parentId;	// 上一级地区ID
    private int    level;		// 1,2,3,4表示省市区场地
    private String scode;		// 拼音简称
    private String name;		// 地区名称
    private String path;		// path路径
    private long   createTime;	// 创建时间
    private long   createBy;	// 创建时间
    private long   modifyTime;	// 修改时间
    private long   modifyBy;	// 最后修改时间
}
