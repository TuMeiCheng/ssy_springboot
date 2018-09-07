package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;

public class Item extends Model<Item> {

    private static final long serialVersionUID = -292387099475224462L;

    private Integer    itemId;		// 器材ID
    private Integer    orgId;		// 管辖机构ID
    private Integer   agencyId;	// 管理公司ID
    private Integer    areaId;		// 场地ID
    private String url;			// URL路径
    private String qzcode;		// 二维码前缀
    private String itemsn;		// 器材编号
    private Integer    eqpsortId;	// 器材分类ID
    private Integer    eqpId;		// 器材ID
    private Integer    supplierId;	// 供应商ID
    private Long   installTime;	// 安装时间
    private Long   expireTime;	// 到期时间
    private Integer    standardcode;// 器材标准:0表示无,1无标准 2老国际 3新国际
    private Integer    provideWay;	// 提供方式 0为无  1 政府提供 2自建 3未知
    private Integer   flowBy;		// 巡检人ID
    private Integer   repairBy;	// 维修人ID
    private Integer    status;		// 器材状态,0未安装,1正常,2报修,3报废
    private Integer    isdel;		// 软删除标志 0正常 1删除
    private Long   createTime;	// 创建时间
    private Integer   createBy;	// 创建人
    private Long   modifyTime;	// 修改时间
    private Integer   modifyBy;	// 最后修改人
    private Integer    itemType;       //'1器材码管理, 2产地码管理'


//=================================================================================================

    public Integer getItemId(){
        return get("ItemId");
    }

    public void setItemId(Integer itemId){
        set("itemId",itemId);
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

    public String getUrl() {
        return get("url"); 
    }

    public void setUrl(String url) {
        set("url",url);
    }

    public String getQzcode() {
        return get("qzcode"); 
    }

    public void setQzcode(String qzcode) {
        set("qzcode",qzcode);
    }

    public String getItemsn() {
        return get("itemsn"); 
    }

    public void setItemsn(String itemsn) {
        set("itemsn",itemsn);
    }

    public Integer getEqpsortId() {
        return get("eqpsortId");
    }

    public void setEqpsortId(Integer eqpsortId) {
        set("eqpsortId",eqpsortId);
    }

    public Integer getEqpId() {
        return get("eqpId"); 
    }

    public void setEqpId(Integer eqpId) {
        set("eqpId",eqpId);
    }

    public Integer getSupplierId() {
        return get("supplierId"); 
    }

    public void setSupplierId(Integer supplierId) {
        set("supplierId",supplierId);
    }

    public Long getInstallTime() {
        return get("installTime"); 
    }

    public void setInstallTime(Long installTime) {
        set("installTime",installTime);
    }

    public Long getExpireTime() {
        return get("expireTime"); 
    }

    public void setExpireTime(Long expireTime) {
        set("expireTime",expireTime);
    }

    public Integer getStandardcode() {
        return get("standardcode"); 
    }

    public void setStandardcode(Integer standardcode) {
        set("standardcode",standardcode);
    }

    public Integer getProvideWay() {
        return get("provideWay"); 
    }

    public void setProvideWay(Integer provideWay) {
        set("provideWay",provideWay);
    }

    public Integer getFlowBy() {
        return get("flowBy"); 
    }

    public void setFlowBy(Integer flowBy) {
        set("flowBy",flowBy);
    }

    public Integer getRepairBy() {
        return get("repairBy"); 
    }

    public void setRepairBy(Integer repairBy) {
        set("repairBy",repairBy);
    }

    public Integer getStatus() {
        return get("status"); 
    }

    public void setStatus(Integer status) {
        set("status",status);
    }

    public Integer getIsdel() {
        return get("isdel"); 
    }

    public void setIsdel(Integer isdel) {
        set("isdel",isdel);
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

    public Integer getItemType() {
        return get("itemType");
    }

    public void setItemType(Integer itemType) {
        set("itemType",itemType);
    }
}
