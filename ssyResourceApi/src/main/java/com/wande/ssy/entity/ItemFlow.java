package com.wande.ssy.entity;


import com.jfinal.plugin.activerecord.Model;

public class ItemFlow extends Model<ItemFlow> {
    private static final Long serialVersionUID = 63319165378654688L;

    private Integer    itemflowId;	// 器材巡检主键
    private Integer    orgId;		// 管辖机构ID
    private Integer   agencyId;	// 管理公司ID
    private Integer    areaId;		// 场地ID
    private Integer    itemId;		// 器材ID
    private String actionName;	// 巡检操作
    private Integer    statusOld;	// 巡检前状态
    private Integer    statusNew;	// 维修前状态
    private Integer    followId;	// 维修记录ID(同表)
    private Integer    reasonType;	// 报修理由类别
    private String imgsnap;		// 损坏图片快照
    private String remark;		// 备注
    private Integer    sourcefrom;	// 巡检来源(1维修人员,2市民)
    private String ip;			// 巡检IP
    private double locationLng;	// 经度值
    private double locationLat;	// 纬度值
    private Integer    check;		// 1合格 0不在范围内巡检
    private Long   createTime;	// 创建时间
    private Integer   createBy;	// 创建人
    private Long   modifyTime;	// 修改时间
    private Integer   modifyBy;		// 最后修改人

    private Integer    flowType;    // 巡检类型  1器材码管理  2场地码管理

    public Integer getItemflowId() {
       return get("itemflowId");

    }

    public void setItemflowId(Integer itemflowId) {
        set("itemflowId",itemflowId);
        
    }

    public Integer getOrgId() {
        return get("orgId");
    }

    public void setOrgId(Integer orgId) {
        set("orgId",orgId);
        
    }

    public Integer getAgencyId() {
        return get("agencyId");
    }

    public void setAgencyId(Integer agencyId) {
     set("agencyId",agencyId);
     
    }

    public Integer getAreaId() {
        return get("areaId");
    }

    public void setAreaId(Integer areaId) {
       set("areaId",areaId);
       
    }

    public Integer getItemId() {
        return get("itemId");
    }

    public void setItemId(Integer itemId) {
        set("itemId",itemId);
        
    }

    public String getActionName() {
        return get("actionName");
    }

    public void setActionName(String actionName) {
       set("actionName",actionName);
       
    }

    public Integer getStatusOld() {
        return get("statusOld");
    }

    public void setStatusOld(Integer statusOld) {
        set("statusOld",statusOld);
        
    }

    public Integer getStatusNew() {
        return get("statusNew");
    }

    public void setStatusNew(Integer statusNew) {
       set("statusNew",statusNew);
       
    }

    public Integer getFollowId() {
        return get("followId");
    }

    public void setFollowId(Integer followId) {
        set("followId",followId);
        
    }

    public Integer getReasonType() {
        return get("reasonType");
    }

    public void setReasonType(Integer reasonType) {
        set("reasonType",reasonType);
        
    }

    public String getImgsnap() {
        return get("imgsnap");
    }

    public void setImgsnap(String imgsnap) {
        set("imgsnap",imgsnap);
        
    }

    public String getRemark() {
        return get("remark");
    }

    public void setRemark(String remark) {
        set("remark",remark);
        
    }

    public Integer getSourcefrom() {
        return get("sourcefrom");
    }

    public void setSourcefrom(Integer sourcefrom) {
        set("sourcefrom",sourcefrom);
        ;
    }

    public String getIp() {
        return get("ip");
    }

    public void setIp(String ip) {
        set("ip",ip);
        
    }

    public double getLocationLng() {
        return get("locationLng");
    }

    public void setLocationLng(double locationLng) {
       set("locationLng",locationLng);
       
    }

    public double getLocationLat() {
        return get("locationLat");
    }

    public void setLocationLat(double locationLat) {
        set("locationLat",locationLat);
        
    }

    public Integer getCheck() {
        return get("check");
    }

    public void setCheck(Integer check) {
        set("check",check);
        
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

    public Integer getModifyBy() {
        return get("modifyBy");
    }

    public void setModifyBy(Integer modifyBy) {
            set("modifyBy",modifyBy);
            
    }

    public Integer getFlowType() {
        return get("flowType");
    }

    public void setFlowType(Integer flowType) {
       set("flowType",flowType);
       
    }
}
