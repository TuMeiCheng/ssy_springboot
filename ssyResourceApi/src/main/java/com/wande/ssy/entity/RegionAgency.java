package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;
import lombok.Data;

@Data
public class RegionAgency extends Model<RegionAgency> {

    private static final long serialVersionUID = -7633722253292207235L;

    private int    id;			// 关联表
    private String regionIds;	// 代理商管理地区ids
    private long   agencyId;	// 管理公司ID

}
