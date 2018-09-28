package com.wande.ssy.enums;

/**
 * 器材 状态 枚举
 * @author vwFisher(422578659@qq.com)
 * 2016年10月24日下午3:57:02
 */
public enum ItemStatus {
	// 器材 and 场地 状态枚举类共用 ,0正常,1报修,2报废
	NOT_INSTALLED(0, "未安装"),	// 未安装
	NORMAL(1, "正常"),			// 正常
	REPAIR(2, "报修"),			// 报修
	ABANDON(3, "报废");			// 报废
	
	private int value;
	private String name;
	
	private ItemStatus(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
 	
	/**
	 * 不存在返回""
	 * @param value
	 * @return
	 */
 	public static String getName(int value) {
 		for (ItemStatus s : ItemStatus.values()) {
 			if (s.getValue() == value) {
 				return s.name;
 			}
 		}
        return "";
    }
 	
 	public static String getActionname(int status) {
 		if (status == NORMAL.getValue()) 
 			return "报正常";
 		else if (status == REPAIR.getValue())
 			return "报修";
 		else if (status == ABANDON.getValue())
 			return "报废";
 		else 
 			return "维修人员";
 	}
}
