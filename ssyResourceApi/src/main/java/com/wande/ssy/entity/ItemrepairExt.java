package com.wande.ssy.entity;


/**
 * 网络报修实体类(扩展Item信息)
 * @author vwFisher(422578659@qq.com)
 * 2016年11月8日下午10:58:26
 */
public class ItemrepairExt extends ItemRepair {

    private static final long serialVersionUID = -8164896419833665047L;

    private int    eqpId;
    private int    supplierId;
    private String itemsn;
    private long   expireTime;
    private int    itemStatus;


    //========================================================================
    private String orgName;
    private String agencyName;
    private String reasonName;
    private String eqpName;
    private String eqpCatesn;
    private String supplierName;
    private String regionFullName;
    private String areaName;
    private String statusNewName;
    private String itemStatusName;
    private String createDate;

    private String leftTime;
    private String followBy;
    private String followName;
    private String followPhone;

    public String getLeftTime() {
        return get("leftTime");
    }

    public String  getFollowBy() {
        return get("followBy");
    }

    public String getFollowName() {
        return get("followName");
    }

    public String getFollowPhone() {
        return get("followPhone");
    }

    public int getEqpId() {
        return eqpId;
    }

    public void setEqpId(int eqpId) {
        this.eqpId = eqpId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getItemsn() {
        return itemsn;
    }

    public void setItemsn(String itemsn) {
        this.itemsn = itemsn;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public int getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(int itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getOrgName() {
        return get("orgName");
    }

    public String getAgencyName() {
        return get("agencyName");
    }

    public String getReasonName() {
        return get("reasonName");
    }

    public String getEqpName() {
        return get("eqpName");
    }

    public String getEqpCatesn() {
        return get("eqpCatesn");
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public void setEqpName(String eqpName) {
        this.eqpName = eqpName;
    }

    public void setEqpCatesn(String eqpCatesn) {
        this.eqpCatesn = eqpCatesn;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setRegionFullName(String regionFullName) {
        this.regionFullName = regionFullName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setStatusNewName(String statusNewName) {
        this.statusNewName = statusNewName;
    }

    public void setItemStatusName(String itemStatusName) {
        this.itemStatusName = itemStatusName;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setLeftTime(String leftTime) {
        this.leftTime = leftTime;
    }

    public void setFollowBy(String followBy) {
        this.followBy = followBy;
    }

    public void setFollowName(String followName) {
        this.followName = followName;
    }

    public void setFollowPhone(String followPhone) {
        this.followPhone = followPhone;
    }

    public void setOrgName(String orgName) {

        this.orgName = orgName;
    }

    public String getSupplierName() {
        return get("supplierName");
    }

    public String getRegionFullName() {
        return get("regionFullName");
    }

    public String getAreaName() {
        return get("areaName");
    }

    public String getStatusNewName() {
        return get("statusNewName");
    }

    public String getItemStatusName() {
        return get("itemStatusName");
    }

    public String getCreateDate() {
        return get("createDate");
    }
}
