package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;
import lombok.Data;

@Data
public class RegionAgency extends Model<RegionAgency> {

    private static final long serialVersionUID = -7633722253292207235L;

    private Integer    id;			// 关联表
    private String regionIds;	// 代理商管理地区ids
    private Long   agencyId;	// 管理公司ID


    public Integer getId() {
       return get("id");
       
    }

    public void setId(Integer id) {
        set("id",id);
        
    }

    public String getRegionIds() {
        return get("regionIds");
        
    }

    public void setRegionIds(String regionIds) {
        set("regionIds",regionIds);
        
    }

    public Long getAgencyId() {
       return get("agencyId");
       
    }

    public void setAgencyId(Long agencyId) {
       set("agencyId",agencyId);
       
    }
}
