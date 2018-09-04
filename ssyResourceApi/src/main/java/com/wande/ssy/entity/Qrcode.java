package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;
import lombok.Data;

@Data
public class Qrcode extends Model<Qrcode> {

    private static final long serialVersionUID = 7855372480404184610L;

    private int    qrcodeId;	// 自增ID
    private String url;			// 提供给市民报修的网页路径
    private String code;		// 二维码
    private String codePrex;	// 编码前缀
    private long   agencyId;	// 管理公司ID
    private int    exportId;	// 所属导出记录ID
    private int    supplierId;	// 供应商ID
    private int    eqpId;		// 器材ID
    private int    standardcode;// 器材标准:0表示无,1无标准 2老国际 3新国际
    private int    status;		// 0无 1未出厂 2出厂 3使用
    private int    isdel;		// 0正常 1删除
    private long   createTime;	// 创建时间
    private long   createBy;	// 创建人
    private long   modifyTime;	// 修改时间
    private long   modifyBy;	// 修改人
    private int isAreaQrcode;   // 0为器材二维码  1为场地二维码

    private int areaId;			//所属场地id


}
