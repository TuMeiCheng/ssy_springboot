package com.wande.ssy.entity;

import com.jfinal.plugin.activerecord.Model;

public class Log extends Model<Log>{

	private static final long serialVersionUID = 2375182037365766289L;

	private Integer logId;		// 日志ID
	private String code;		// 资源code
	private String desc;		// 描述
	private String account;		// 操作人账号
	private String ip;			// 操作IP
	private Long   createTime;	// 创建时间
	private Integer   createBy;	// 创建人



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

	public int getLogId() {
		return get("logId");
		
	}

	public void setLogId(int logId) {
		set("logId",logId);
		
	}

	public String getDesc() {
		return get("desc");
		
	}

	public void setDesc(String desc) {
		set("desc",desc);
		
	}

	public String getAccount() {
		return get("account");
		
	}

	public void setAccount(String account) {
		set("account",account);
		
	}

	public String getIp() {
		return get("ip");
		
	}

	public void setIp(String ip) {
		set("ip",ip);
		
	}

	public void setCreateTime(long createTime) {
		set("createTime",createTime);
		
	}

	public void setCreateBy(Integer createBy) {
		set("createBy",createBy);
		
	}
}
