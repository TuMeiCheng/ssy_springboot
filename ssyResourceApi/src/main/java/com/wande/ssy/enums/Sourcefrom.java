package com.wande.ssy.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 巡检来源 枚举
 * @author vwFisher(422578659@qq.com)
 * 2016年11月1日下午2:14:42
 */
public enum Sourcefrom {
	//巡检来源(1维修人员,2市民)
	REPAIR(1, "维修人员"),
	CITIZEN(2, "市民");
	
	private int value;
	private String name;
	
	private Sourcefrom(int value, String name) {
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
 		for (Sourcefrom s : Sourcefrom.values()) {
 			if (s.getValue() == value) {
 				return s.name;
 			}
 		}
        return "";
    }
 	
 	public static List<Map<String, Object>> getSelect() {
 		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
 		for (Sourcefrom s : Sourcefrom.values()) {
 			Map<String,Object> m = new HashMap<String,Object>();
 			m.put("sourcefrom", s.getValue());
 			m.put("sourcefromName", s.getName());
 			result.add(m);
 		}
 		return result;
 	}
}
