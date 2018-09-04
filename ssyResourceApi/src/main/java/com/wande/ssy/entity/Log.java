package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;
import lombok.Data;

@Data
public class Log extends Model<Log>{

	private static final long serialVersionUID = 2375182037365766289L;

	private int    logId;		// 日志ID
	private String code;		// 资源code
	private String desc;		// 描述
	private String account;		// 操作人账号
	private String ip;			// 操作IP
	private long   createTime;	// 创建时间
	private long   createBy;	// 创建人



	public void setCreateTime(Long createTime) {
		set("createTime", createTime);
	}

	public Long getCreateTime() {
		return get("createTime");
	}

    public void setCreateBy(Long createBy) { 
		set("createBy", createBy);
	}

	public Long getCreateBy() {
		return get("createBy");
	}

    public void setCode(String Code) { 
		set("Code", Code);
	}

	public String getCode() {
		return get("Code");
	}
    
}
