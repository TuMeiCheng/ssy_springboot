package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;
import lombok.Data;

@Data
public class Org extends Model<Org> {

    private static final long serialVersionUID = -1980930091536923073L;

    private int    orgId;		// 管辖机构ID
    private int    parentId;	// 上级ID
    private String path;		// path路径
    private int    regionId;	// 地区ID
    private String name;		// 管辖机构名称
    private int    level;		// level
    private long   createTime;	// 创建时间
    private long   createBy;	// 创建人
    private long   modifyTime;	// 修改时间
    private long   modifyBy;	// 最后修改人
}
