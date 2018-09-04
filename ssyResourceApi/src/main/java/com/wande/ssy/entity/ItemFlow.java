package com.wande.ssy.entity;


import com.jfinal.plugin.activerecord.Model;
import lombok.Data;

@Data
public class ItemFlow extends Model<ItemFlow> {
    private static final long serialVersionUID = 63319165378654688L;

    private int    itemflowId;	// 器材巡检主键
    private int    orgId;		// 管辖机构ID
    private long   agencyId;	// 管理公司ID
    private int    areaId;		// 场地ID
    private int    itemId;		// 器材ID
    private String actionName;	// 巡检操作
    private int    statusOld;	// 巡检前状态
    private int    statusNew;	// 维修前状态
    private int    followId;	// 维修记录ID(同表)
    private int    reasonType;	// 报修理由类别
    private String imgsnap;		// 损坏图片快照
    private String remark;		// 备注
    private int    sourcefrom;	// 巡检来源(1维修人员,2市民)
    private String ip;			// 巡检IP
    private double locationLng;	// 经度值
    private double locationLat;	// 纬度值
    private int    check;		// 1合格 0不在范围内巡检
    private long   createTime;	// 创建时间
    private long   createBy;	// 创建人
    private long   modifyTime;	// 修改时间
    private long   modifyBy;		// 最后修改人

    private int    flowType;    // 巡检类型  1器材码管理  2场地码管理


}
