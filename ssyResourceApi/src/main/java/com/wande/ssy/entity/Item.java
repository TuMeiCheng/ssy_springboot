package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;
import lombok.Data;

@Data
public class Item extends Model<Item> {

    private static final long serialVersionUID = -292387099475224462L;

    private int    itemId;		// 器材ID
    private int    orgId;		// 管辖机构ID
    private long   agencyId;	// 管理公司ID
    private int    areaId;		// 场地ID
    private String url;			// URL路径
    private String qzcode;		// 二维码前缀
    private String itemsn;		// 器材编号
    private int    eqpsortId;	// 器材分类ID
    private int    eqpId;		// 器材ID
    private int    supplierId;	// 供应商ID
    private long   installTime;	// 安装时间
    private long   expireTime;	// 到期时间
    private int    standardcode;// 器材标准:0表示无,1无标准 2老国际 3新国际
    private int    provideWay;	// 提供方式 0为无  1 政府提供 2自建 3未知
    private long   flowBy;		// 巡检人ID
    private long   repairBy;	// 维修人ID
    private int    status;		// 器材状态,0未安装,1正常,2报修,3报废
    private int    isdel;		// 软删除标志 0正常 1删除
    private long   createTime;	// 创建时间
    private long   createBy;	// 创建人
    private long   modifyTime;	// 修改时间
    private long   modifyBy;	// 最后修改人
    private int    itemType;       //'1器材码管理, 2产地码管理'
}
