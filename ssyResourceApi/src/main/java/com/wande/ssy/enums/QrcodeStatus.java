package com.wande.ssy.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 二维码状态枚举
 * @author vwFisher(422578659@qq.com)
 * 2016年11月10日下午11:29:43
 */
public enum QrcodeStatus {
	//状态 0无 1未出厂 2出厂 3使用
	NONE(0, "无"),		// 0无
	UNOUT(1, "未出厂"),	// 1未出厂
	OUT(2, "出厂"),		// 2出厂
	USE(3, "使用");		// 3使用
	
	private int value;
	private String name;
	
	private QrcodeStatus(int value, String name) {
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
 		for (QrcodeStatus s : QrcodeStatus.values()) {
 			if (s.getValue() == value) {
 				return s.name;
 			}
 		}
        return "";
    }
 	
 	public static List<Map<String, Object>> getSelect() {
 		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
 		for (QrcodeStatus s : QrcodeStatus.values()) {
 			Map<String,Object> m = new HashMap<String,Object>();
 			m.put("status", s.getValue());
 			m.put("statusName", s.getName());
 			result.add(m);
 		}
 		return result;
 	}
}
