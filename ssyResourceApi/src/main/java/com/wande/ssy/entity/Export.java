package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;
import lombok.Data;

@Data
public class Export extends Model<Export> {


    private static final long serialVersionUID = 5761728294152887173L;

    private int  exportId;		// 导出ID
    private int  exportNum;		// 导出数量
    private long exportTime;	// 导出时间
    private long exportBy;		// 导出人


}
