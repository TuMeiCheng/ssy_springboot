package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;

public class AreaFlow extends Model<AreaFlow> {
    private static final long serialVersionUID = -1488156894769684490L;

    private String areaflowId  = "areaflowId";	// 场地巡检ID
    private String orgId       = "orgId";		// 管辖机构ID
    private String agencyId    = "agencyId";	// 管理公司ID
    private String areaId      = "areaId";		// 场地ID
    private String imgsnap     = "imgsnap";		// 损坏图片快照
    private String remark      = "remark";		// 备注
    private String ip          = "ip";			// 巡检IP
    private String locationLng = "locationLng";	// 经度值
    private String locationLat = "locationLat";	// 纬度值
    private String check       = "check";		// 1合格 0不在范围内巡检
    private String createTime  = "createTime";	// 创建时间
    private String createBy    = "createBy";	// 创建人

    public Integer getAreaflowId() {
        return getInt(areaflowId);
    }

    public void setAreaflowId(int areaflowId) {
        set(this.areaflowId, areaflowId);
    }

    public Integer getOrgId() {
        return getInt(orgId);
    }

    public void setOrgId(int orgId) {
        set(this.orgId, orgId);
    }

    public Long getAgencyId() {
        return getLong(agencyId);
    }

    public void setAgencyId(long agencyId) {
        set(this.agencyId, agencyId);
    }

    public Integer getAreaId() {
        return getInt(areaId);
    }

    public void setAreaId(int areaId) {
        set(this.areaId, areaId);
    }

    public String getImgsnap() {
        return getStr(imgsnap);
    }

    public void setImgsnap(String imgsnap) {
        set(this.imgsnap, imgsnap);
    }
    public String getRemark() {
        return getStr(remark);
    }

    public void setRemark(String remark) {
        set(this.remark, remark);
    }
    public String getIp() {
        return getStr(ip);
    }

    public void setIp(String ip) {
        set(this.ip, ip);
    }
    public Double getLocationLng() {
        return getDouble(locationLng);
    }

    public void setLocationLng(double locationLng) {
        set(this.locationLng, locationLng);
    }

    public Double getLocationLat() {
        return getDouble(locationLat);
    }

    public void setLocationLat(double locationLat) {
        set(this.locationLat, locationLat);
    }

    public Integer getCheck() {
        return getInt(check);
    }

    public void setCheck(int check) {
        set(this.check, check);
    }

    public Long getCreateTime() {
        return getLong(createTime);
    }

    public void setCreateTime(long createTime) {
        set(this.createTime, createTime);
    }

    public Long getCreateBy() {
        return getLong(createBy);
    }

    public void setCreateBy(long createBy) {
        set(this.createBy, createBy);
    }

}
