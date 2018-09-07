package com.wande.ssy.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 器材提供方式 枚举
 * @author vwFisher(422578659@qq.com)
 * 2016年10月24日下午3:20:06
 */
public enum ProvideWay {
	// 提供方式 0为无  1 政府提供 2自建 3未知
	NONE(0, "无"),			// 无
	GOVERNMENT(1, "政府提供"),	// 政府提供
	SELF(2, "自建"),			// 自建
	UNKNOWN(3, "未知");		// 未知
	
	private int value;
	private String name;
	
	private ProvideWay(int value, String name) {
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
 		for (ProvideWay s : ProvideWay.values()) {
 			if (s.getValue() == value) {
 				return s.name;
 			}
 		}
        return "";
    }
 	
 	public static List<Map<String, Object>> getSelect() {
 		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
 		for (ProvideWay s : ProvideWay.values()) {
 			Map<String,Object> m = new HashMap<String,Object>();
 			m.put("provideWay", s.getValue());
 			m.put("provideWayName", s.getName());
 			result.add(m);
 		}
 		return result;
 	}
}
