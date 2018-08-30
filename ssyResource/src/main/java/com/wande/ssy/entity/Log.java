package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;

public class Log extends Model<Log>{


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
