package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;

import javax.validation.constraints.NotNull;

public class Eqp extends  Model<Eqp> {

    private String eqpId      = "eqpId";		// 器材库ID
    private String eqpsortId  = "eqpsortId";	// 器材分类
    private String catesn     = "catesn";		// 器材规格型号
    private String name       = "name";			// 器材名称
    private String supplierId = "supplierId";	// 所属供应商ID
    private String imgurl     = "imgurl";		// 器材图片
    private String video      = "video";		// 器材视频
    private String videoImg   = "videoImg";	// 视频的缩略图
    private String status     = "status";		// 启用，禁止
    private String createTime = "createTime";	// 创建时间
    private String createBy   = "createBy";		// 创建人
    private String modifyTime = "modifyTime";	// 修改时间
    private String modifyBy   = "modifyBy";		// 最后修改人

    private static final long serialVersionUID = -2078180361107464818L;
    public static final Eqp dao = new Eqp();

    @NotNull(message = "器材id不能为空！")
    public Integer getEqpId() {
        return getInt(eqpId);
    }

    public void setEqpId(int eqpId) {
        set(this.eqpId, eqpId);
    }

    @NotNull(message = "器材分类不能为空!")
    public Integer getEqpsortId() {
        return getInt(eqpsortId);
    }

    public void setEqpsortId(int eqpsortId) {
        set(this.eqpsortId, eqpsortId);
    }

    public String getCatesn() {
        return getStr(catesn);
    }

    public void setCatesn(String catesn) {
        set(this.catesn, catesn);
    }
    @NotNull(message = "器材名称不能为空!")
    public String getName() {
        return getStr(name);
    }

    public void setName(String name) {
        set(this.name, name);
    }
    @NotNull(message = "所属供应商ID不能为空!")
    public Integer getSupplierId() {
        return getInt(supplierId);
    }

    public void setSupplierId(int supplierId) {
        set(this.supplierId, supplierId);
    }

    public String getImgurl() {
        return getStr(imgurl);
    }

    public void setImgurl(String imgurl) {
        set(this.imgurl, imgurl);
    }
    public String getVideo() {
        return getStr(video);
    }

    public void setVideo(String video) {
        set(this.video, video);
    }
    public String getVideoImg() {
        return getStr(videoImg);
    }

    public void setVideoImg(String videoImg) {
        set(this.videoImg, videoImg);
    }

    @NotNull(message = "器材状态不能为空！")
    public Integer getStatus() {
        return getInt(status);
    }

    public void setStatus(int status) {
        set(this.status, status);
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

    public Long getModifyTime() {
        return getLong(modifyTime);
    }

    public void setModifyTime(long modifyTime) {
        set(this.modifyTime, modifyTime);
    }

    public Long getModifyBy() {
        return getLong(modifyBy);
    }

    public void setModifyBy(long modifyBy) {
        set(this.modifyBy, modifyBy);
    }

    //=========================================================
    public String  getSupplierName() {
        return get("supplierName");
    }

    public String getEqpsortName() {
        return get("eqpsortName");
    }
}
