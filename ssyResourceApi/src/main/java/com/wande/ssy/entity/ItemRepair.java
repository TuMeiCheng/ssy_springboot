package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;
import lombok.Data;

@Data
public class ItemRepair extends Model<ItemRepair> {

    private static final long serialVersionUID = -6096356516306009085L;

    private int    repairId;		// 网络报修主键
    private int    orgId;			// 管辖机构ID
    private long   agencyId;		// 管理公司ID
    private int    areaId;			// 场地ID
    private int    itemId;			// 器材ID
    private int    itemflowId;		// 巡检记录ID
    private int    reasonType;		// 报修理由类别
    private String imgsnap;			// 损坏图片快照
    private String remark;			// 备注
    private String acceptResult;	// 跟进结果
    private String ip;				// 巡检IP
    private int    status;			// 0待跟进 1已跟进  维修单的状态
    private long   createTime;		// 创建时间
    private long   createBy;		// 创建人
    private long   modifyTime;		// 修改时间
    private long   modifyBy;		// 最后修改人
}
