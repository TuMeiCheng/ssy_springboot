package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;

public class Item extends Model<Item> {

    private static final long serialVersionUID = -292387099475224462L;

    private Long    itemId;		// 器材ID
    private Long    orgId;		// 管辖机构ID
    private Long   agencyId;	// 管理公司ID
    private Long    areaId;		// 场地ID
    private String url;			// URL路径
    private String qzcode;		// 二维码前缀
    private String itemsn;		// 器材编号
    private Long    eqpsortId;	// 器材分类ID
    private Long    eqpId;		// 器材ID
    private Long    supplierId;	// 供应商ID
    private Long   installTime;	// 安装时间
    private Long   expireTime;	// 到期时间
    private Long    standardcode;// 器材标准:0表示无,1无标准 2老国际 3新国际
    private Long    provideWay;	// 提供方式 0为无  1 政府提供 2自建 3未知
    private Long   flowBy;		// 巡检人ID
    private Long   repairBy;	// 维修人ID
    private Long    status;		// 器材状态,0未安装,1正常,2报修,3报废
    private Long    isdel;		// 软删除标志 0正常 1删除
    private Long   createTime;	// 创建时间
    private Long   createBy;	// 创建人
    private Long   modifyTime;	// 修改时间
    private Long   modifyBy;	// 最后修改人
    private Long   itemType;       //'1器材码管理, 2产地码管理'


//=================================================================================================

    public Long getItemId(){
        return get("ItemId");
    }

    public void setItemId(Long itemId){
        set("itemId",itemId);
    }

    public Long getOrgId() {
        return get("orgId"); 
    }

    public void setOrgId(Long orgId) {
        set("orgId",orgId);
    }

    public Long getAgencyId() {
        return get("agencyId"); 
    }

    public void setAgencyId(Long agencyId) {
        set("agencyId",agencyId);
    }

    public Long getAreaId() {
        return get("areaId");
    }

    public void setAreaId(Long areaId) {
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

    public Long getEqpsortId() {
        return get("eqpsortId");
    }

    public void setEqpsortId(Long eqpsortId) {
        set("eqpsortId",eqpsortId);
    }

    public Long getEqpId() {
        return get("eqpId"); 
    }

    public void setEqpId(Long eqpId) {
        set("eqpId",eqpId);
    }

    public Long getSupplierId() {
        return get("supplierId"); 
    }

    public void setSupplierId(Long supplierId) {
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

    public Long getStandardcode() {
        return get("standardcode"); 
    }

    public void setStandardcode(Long standardcode) {
        set("standardcode",standardcode);
    }

    public Long getProvideWay() {
        return get("provideWay"); 
    }

    public void setProvideWay(Long provideWay) {
        set("provideWay",provideWay);
    }

    public Long getFlowBy() {
        return get("flowBy"); 
    }

    public void setFlowBy(Long flowBy) {
        set("flowBy",flowBy);
    }

    public Long getRepairBy() {
        return get("repairBy"); 
    }

    public void setRepairBy(Long repairBy) {
        set("repairBy",repairBy);
    }

    public Long getStatus() {
        return get("status"); 
    }

    public void setStatus(Long status) {
        set("status",status);
    }

    public Long getIsdel() {
        return get("isdel"); 
    }

    public void setIsdel(Long isdel) {
        set("isdel",isdel);
    }

    public Long getCreateTime() {
        return get("createTime"); 
    }

    public void setCreateTime(Long createTime) {
        set("createTime",createTime);
    }

    public Long getCreateBy() {
        return get("createBy"); 
    }

    public void setCreateBy(Long createBy) {
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

    public Long getItemType() {
        return get("itemType");
    }

    public void setItemType(Long itemType) {
        set("itemType",itemType);
    }
}
