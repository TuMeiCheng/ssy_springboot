package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;

public class Export extends Model<Export> {


    private static final long serialVersionUID = 5761728294152887173L;

    private Integer  exportId;		// 导出ID
    private Integer  exportNum;		// 导出数量
    private Long exportTime;	// 导出时间
    private Integer exportBy;		// 导出人

    public Integer getExportId() {
        return get("exportId");

    }

    public void setExportId(Integer exportId) {
            set("exportId",exportId);

    }

    public Integer getExportNum() {
        return get("exportNum");
    }

    public void setExportNum(Integer exportNum) {
        set("exportNum",exportNum);
        
    }

    public Long getExportTime() {
        return get("exportTime");
    }

    public void setExportTime(Long exportTime) {
       set("exportTime",exportTime);
       
    }

    public Integer getExportBy() {
        return get("exportBy");
    }

    public void setExportBy(Integer exportBy) {
       set("exportBy",exportBy);
       
    }
}
