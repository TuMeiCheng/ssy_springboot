package com.wande.ssy.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 器材分类顶级分类枚举
 * @author vwFisher(422578659@qq.com)
 * 2017年1月13日上午10:32:47
 */
public enum EqpProjectSort {
	//0无  1健身器材  2游乐设备  3笼式多功能球场(先保留字段), 4设施(公园内树木)
	Fitness(1, "健身器材"),
	Amusement(2, "游乐设备"),
	Multifunctional(3, "笼式多功能球场"),
	Facilities(4, "设施");
	
	private int value;
	private String name;
	
	private EqpProjectSort(int value, String name) {
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
 		for (EqpProjectSort s : EqpProjectSort.values()) {
 			if (s.getValue() == value) {
 				return s.name;
 			}
 		}
        return "";
    }
 	
 	public static List<Map<String, Object>> getSelect() {
 		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
 		for (EqpProjectSort s : EqpProjectSort.values()) {
 			Map<String,Object> m = new HashMap<String,Object>();
 			m.put("projectId", s.getValue());
 			m.put("projectName", s.getName());
 			result.add(m);
 		}
 		return result;
 	}
 	
 	public static boolean isExist(int value){
 		for (EqpProjectSort s : EqpProjectSort.values()) {
 			if (s.getValue() == value) {
 				return true;
 			}
 		}
        return false;
 	}
}
