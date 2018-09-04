package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;
import lombok.Data;

@Data
public class Supplier extends Model<Supplier> {

    private static final long serialVersionUID = -3787690618147673790L;

    private int    supplierId;		// 主键
    private String csn;				// 供应商编号
    private String name;			// 供应商名称
    private String contactTel;		// 联系电话
    private String contactPerson;	// 联系人
    private int    status;			// 状态 0启用 1禁用
    private long   createTime;		// 创建时间
    private long   createBy;		// 创建人
    private long   modifyTime;		// 修改时间
    private long   modifyBy;		// 最后修改人

}
