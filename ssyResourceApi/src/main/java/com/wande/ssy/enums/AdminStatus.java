package com.wande.ssy.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户状态枚举
 * @author vwFisher(422578659@qq.com)
 * 2017年1月13日上午10:32:47
 */
public enum AdminStatus {
	//状态 0 正常 10 禁用
	NORMAL(0, "正常"),	// 0正常
	BAN(10, "冻结");		// 10禁用
	
	private Integer value;
	private String name;
	
	private AdminStatus(Integer value, String name) {
		this.value = value;
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
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
 	public static String getName(Integer value) {
 		for (AdminStatus s : AdminStatus.values()) {
 			if (s.getValue() == value) {
 				return s.name;
 			}
 		}
        return "";
    }
 	
 	public static List<Map<String, Object>> getSelect() {
 		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
 		for (AdminStatus s : AdminStatus.values()) {
 			Map<String,Object> m = new HashMap<String,Object>();
 			m.put("status", s.getValue());
 			m.put("statusName", s.getName());
 			result.add(m);
 		}
 		return result;
 	}
}
