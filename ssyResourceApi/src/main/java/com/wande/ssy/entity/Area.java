package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;

import javax.validation.constraints.NotNull;

public class Area extends Model<Area> {

    private Integer areaId;       		// 场地ID
    private Integer orgId;			    // 管辖机构ID
    private Integer agencyId;			// 管理公司ID
    private Integer provideWay;		    // 提供方式 0为无  1 政府提供 2自建 3未知
    private String name;				// 地点名称
    private String regionFullName;	    // 地区全名
    private Integer regionId;			// 行政区域ID(外键)
    private Double longitude;		    // 经度值
    private Double latitude;			// 纬度值
    private Integer checkRange;		    // 巡检范围
    private Long createTime;		    // 创建时间
    private Integer createBy;			// 创建人
    private Long modifyTime;		    // 修改时间
    private Integer modifyBy;			// 最后修改人

    private int    areaType;        //场地类别，1室外，2室内，3，公园

    private String qrcode;              // 场地二维码
    private Integer status;     	    // 场地状态,0未安装,1正常,2报修,3报废   具体代码不代表真实业务
    private Integer flowBy;			    // 巡检人ID
    private Integer repairBy;		    // 维修人ID
    private Integer qrcodeType;         // 场地管理方式  1 器材码管理   2场地码管理


    public String getRegionName(){
        return getStr("regionName");
    }

    public String getOrgName(){
        return getStr("orgName");
    }

    public String getProvideWayName(){
        return getStr("provideWayName");
    }

    public String getAreaTypeName(){ return getStr("areaTypeName");}

    public String getUrl(){
        return getStr("url");
    }


    //==========================================================================================================


    @NotNull(message = "场地管理方式必需选择!")
    public Integer getQrcodeType(){
        return getInt("qrcodeType");
    }

    public void setQrcodeType(int qrcodeType){
        set("qrcodeType", qrcodeType);
    }

    public Long getFlowBy() {
        return get("flowBy");
    }

    public void setFlowBy(Long flowBy) {
        set("flowBy", flowBy);
    }

    public Long getRepairBy() {
        return get("repairBy");
    }

    public void setRepairBy(Long repairBy) {
        set("repairBy", repairBy);
    }

    public String getQrcode(){
        return getStr("qrcode");
    }

    public void setQrcode(String qrcode){
        set("qrcode", qrcode);
    }

    public Integer getStatus() {
        return getInt("status");
    }

    public void setStatus(int status) {
        set("status", status);
    }

    @NotNull(message = "场地id不能为空！")
    public Integer getAreaId() {
        return getInt("areaId");
    }

    public void setAreaId(int areaId) {
        set("areaId", areaId);
    }

    @NotNull(message = "管辖机构ID不能为空!")
    public Integer getOrgId() {
        return getInt("orgId");
    }

    public void setOrgId(int orgId) {
        set("orgId", orgId);
    }

    public Long getAgencyId() {
        return getLong("agencyId");
    }

    public void setAgencyId(long agencyId) {
        set("agencyId", agencyId);
    }

    @NotNull(message = "提供方式不能为空!")
    public Integer getProvideWay() {
        return getInt("provideWay");
    }

    public void setProvideWay(int provideWay) {
        set("provideWay", provideWay);
    }

    @NotNull(message = "地点名称不能为空!")
    public String getName() {
        return getStr("name");
    }

    public void setName(String name) {
        set("name", name);
    }
    public String getRegionFullName() {
        return getStr("regionFullName");
    }

    public void setRegionFullName(String regionFullName) {
        set("regionFullName", regionFullName);
    }
    @NotNull(message = "行政区域ID(外键)不能为空!")
    public Integer getRegionId() {
        return getInt("regionId");
    }

    public void setRegionId(int regionId) {
        set("regionId", regionId);
    }

    @NotNull(message = "经度值不能为空!")
    public Double getLongitude() {
        return getDouble("longitude");
    }

    public void setLongitude(double longitude) {
        set("longitude", longitude);
    }

    @NotNull(message = "纬度值不能为空!")
    public Double getLatitude() {
        return getDouble("latitude");
    }

    public void setLatitude(double latitude) {
        set("latitude", latitude);
    }

    @NotNull(message = "巡检范围不能为空!")
    public Integer getCheckRange() {
        return getInt("checkRange");
    }

    public void setCheckRange(int checkRange) {
        set("checkRange", checkRange);
    }

    public Long getCreateTime() {
        return getLong("createTime");
    }

    public void setCreateTime(long createTime) {
        set("createTime", createTime);
    }

    public Long getCreateBy() {
        return getLong("createBy");
    }

    public void setCreateBy(long createBy) {
        set("createBy", createBy);
    }

    public Long getModifyTime() {
        return getLong("modifyTime");
    }

    public void setModifyTime(long modifyTime) {
        set("modifyTime", modifyTime);
    }

    public Long getModifyBy() {
        return getLong("modifyBy");
    }

    public void setModifyBy(long modifyBy) {
        set("modifyBy", modifyBy);
    }

    @NotNull(message = "场地分类必需选择!")
    public Integer getAreaType() {
        return get("areaType");
    }

    public void setAreaType(int areaType) {
        set("areaType",areaType);
    }

}
