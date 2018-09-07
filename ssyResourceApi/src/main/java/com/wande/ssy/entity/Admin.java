package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;
import lombok.Data;

@Data
public class Admin extends Model<Admin> {

    private static final long serialVersionUID = -389434528308632959L;

    private long   uin;			// 系统用户ID
    private int    roleId;		// 1管理员 2管理公司 3 体育局
    private int    orgId;		// 体育局ID
    private String account;		// 登录账号
    private String name;		// 姓名
    private String email;		// 邮箱
    private String phone;		// 电话
    private int    status;		// 状态 0 正常 10 禁用
    private long   loginTime;	// 上一次登录时间
    private String loginIp;		// 最后登录IP
    private long   createTime;	// 创建时间
    private long   createBy;	// 创建人
    private long   modifyTime;	// 修改时间
    private long   modifyBy;	// 最后修改人

}
