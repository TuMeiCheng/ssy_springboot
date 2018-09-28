package com.wande.ssy.entity;

public class ItemflowExt extends ItemFlow {

    //String select = "select a.*,b.installTime, b.expireTime,b.eqpId,b.supplierId,b.repairBy,b.areaId,b.itemsn,b.eqpsortId";
    private static final long serialVersionUID = -6134099299405482967L;

    private int    status;		// 器材状态,0未安装,1正常,2报修,3报废
    private long   installTime;	// 器材 安装时间
    private long   expireTime;	// 器材到期时间
    private int    eqpId;		// 器材ID
    private int    supplierId;	// 供应商ID
    private long   repairBy;	// 维修人(user表Id)
    private String itemsn;		// 器材编号
    private int    eqpsortId;	// 器材分类ID

    //===================================================
    //以下属性只有get方法，方便转换json数据给前端取值
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
    private String  createName;
    private String createPhone;
    private String repairPhone;
    private String repairTime;
    private String repairDate;
    private String installDate;
    private String  expireDate;
    private String sourcefromName;
    private String repairName;









    public Integer getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getInstallTime() {
        return installTime;
    }

    public void setInstallTime(long installTime) {
        this.installTime = installTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
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

    public long getRepairBy() {
        return repairBy;
    }

    public void setRepairBy(long repairBy) {
        this.repairBy = repairBy;
    }

    public String getItemsn() {
        return itemsn;
    }

    public void setItemsn(String itemsn) {
        this.itemsn = itemsn;
    }

    public int getEqpsortId() {
        return eqpsortId;
    }

    public void setEqpsortId(int eqpsortId) {
        this.eqpsortId = eqpsortId;
    }

    //========================================================================
    //以下属性只有get方法，方便转换json数据给前端取值
    public String getLeftTime() {
        return get("leftTime");
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

    public String getStatusOldName() {
        return get("statusOldName");
    }

    public String getItemStatusName() {
        return get("itemStatusName");
    }

    public String getCreateDate() {
        return get("createDate");
    }

    public String getSourcefromName() {
        return get("sourcefromName");
    }

    public String getCreateName() {
        return get("createName");
    }

    public String getCreatePhone() {
        return get("createPhone");
    }

    public String getRepairName() {
        return get("repairName");
    }

    public String getRepairPhone() {
        return get("repairPhone");
    }

    public String getRepairTime() {
        return get("repairTime");
    }

    public String getRepairDate() {
        return get("repairDate");
    }

    public String getInstallDate() {
        return get("installDate");
    }

    public String getExpireDate() {
        return get("expireDate");
    }


    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public void setCreatePhone(String createPhone) {
        this.createPhone = createPhone;
    }

    public void setRepairPhone(String repairPhone) {
        this.repairPhone = repairPhone;
    }

    public void setRepairTime(String repairTime) {
        this.repairTime = repairTime;
    }

    public void setRepairDate(String repairDate) {
        this.repairDate = repairDate;
    }

    public void setInstallDate(String installDate) {
        this.installDate = installDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public void setSourcefromName(String sourcefromName) {
        this.sourcefromName = sourcefromName;
    }

    public void setRepairName(String repairName) {
        this.repairName = repairName;
    }
}
