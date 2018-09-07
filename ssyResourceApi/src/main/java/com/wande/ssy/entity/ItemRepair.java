package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;

public class ItemRepair extends Model<ItemRepair> {

    private static final long serialVersionUID = -6096356516306009085L;

    private Integer    repairId;		// 网络报修主键
    private Integer    orgId;			// 管辖机构ID
    private Integer   agencyId;		    // 管理公司ID
    private Integer    areaId;			// 场地ID
    private Integer    itemId;			// 器材ID
    private Integer    itemflowId;		// 巡检记录ID
    private Integer    reasonType;		// 报修理由类别
    private String imgsnap;			    // 损坏图片快照
    private String remark;			    // 备注
    private String acceptResult;	    // 跟进结果
    private String ip;				    // 巡检IP
    private Integer status;			    // 0待跟进 1已跟进  维修单的状态
    private Long   createTime;		    // 创建时间
    private Integer   createBy;		    // 创建人
    private Long   modifyTime;		    // 修改时间
    private Integer   modifyBy;		    // 最后修改人


    //============================================================================================


    public Integer getRepairId() {
      return get("repairId");
    }

    public void setRepairId(Integer repairId) {
        set("repairId",repairId);
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

    public Integer getItemflowId() {
        return get("itemflowId");

    }

    public void setItemflowId(Integer itemflowId) {
        set("itemflowId",itemflowId);
        
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

    public String getAcceptResult() {
        return get("acceptResult");

    }

    public void setAcceptResult(String acceptResult) {
        set("acceptResult",acceptResult);
        
    }

    public String getIp() {
        return get("ip");

    }

    public void setIp(String ip) {
        set("ip",ip);
    }

    public Integer getStatus() {
        return get("status");
    }

    public void setStatus(Integer status) {
       set("status",status);
       
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
}
