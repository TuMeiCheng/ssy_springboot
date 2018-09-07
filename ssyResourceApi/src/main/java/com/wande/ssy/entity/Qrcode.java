package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;


public class Qrcode extends Model<Qrcode> {

    private static final Long serialVersionUID = 7855372480404184610L;

    private Integer   qrcodeId;	// 自增ID
    private String url;			// 提供给市民报修的网页路径
    private String code;		// 二维码
    private String codePrex;	// 编码前缀
    private Integer   agencyId;	// 管理公司ID
    private Integer    exportId;	// 所属导出记录ID
    private Integer    supplierId;	// 供应商ID
    private Integer    eqpId;		// 器材ID
    private Integer    standardcode;// 器材标准:0表示无,1无标准 2老国际 3新国际
    private Integer    status;		// 0无 1未出厂 2出厂 3使用
    private Integer    isdel;		// 0正常 1删除
    private Long   createTime;	// 创建时间
    private Integer   createBy;	// 创建人
    private Long   modifyTime;	// 修改时间
    private Integer   modifyBy;	// 修改人
    private Integer isAreaQrcode;   // 0为器材二维码  1为场地二维码

    private Integer areaId;			//所属场地id


    public Integer getQrcodeId() {
       return get("qrcodeId");

    }

    public void setQrcodeId(Integer qrcodeId) {
       set("qrcodeId",qrcodeId);
       
    }

    public String getUrl() {
       return get("url");

    }

    public void setUrl(String url) {
        set("url",url);
    }

    public String getCode() {
       return get("code");

    }

    public void setCode(String code) {
        set("code",code);
        
    }

    public String getCodePrex() {
       return get("codePrex");

    }

    public void setCodePrex(String codePrex) {
       set("codePrex",codePrex);
       
    }

    public Integer getAgencyId() {
        return get("agencyId");

    }

    public void setAgencyId(Integer agencyId) {
        set("agencyId",agencyId);
        
    }

    public Integer getExportId() {
       return get("exportId");

    }

    public void setExportId(Integer exportId) {
       set("exportId",exportId);
       
    }

    public Integer getSupplierId() {
        return get("supplierId");

    }

    public void setSupplierId(Integer supplierId) {
       set("supplierId",supplierId);
       
    }

    public Integer getEqpId() {
       return get("eqpId");

    }

    public void setEqpId(Integer eqpId) {
        set("eqpId",eqpId);
    }

    public Integer getStandardcode() {
       return get("standardcode");

    }

    public void setStandardcode(Integer standardcode) {
       set("standardcode",standardcode);
       
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

    public Integer getIsAreaQrcode() {
       return get("isAreaQrcode");

    }

    public void setIsAreaQrcode(Integer isAreaQrcode) {
       set("isAreaQrcode",isAreaQrcode);
       
    }

    public Integer getAreaId() {
       return get("areaId");

    }

    public void setAreaId(Integer areaId) {
       set("areaId",areaId);
       
    }
}
