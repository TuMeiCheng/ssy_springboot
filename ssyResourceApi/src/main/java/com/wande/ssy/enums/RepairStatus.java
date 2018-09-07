package com.wande.ssy.enums;

/**
 * 网络报修 状态 枚举
 * @author vwFisher(422578659@qq.com)
 * 2016年11月1日下午2:24:51
 */
public enum RepairStatus {
	//0待跟进 1已跟进  维修单的状态
	UNFOLLOW(0, "待跟进"),	// 0待跟进
	FOLLOW(1, "已跟进");		// 1已跟进
	
	private int value;
	private String name;
	
	private RepairStatus(int value, String name) {
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
 		for (RepairStatus s : RepairStatus.values()) {
 			if (s.getValue() == value) {
 				return s.name;
 			}
 		}
        return "";
    }
}
