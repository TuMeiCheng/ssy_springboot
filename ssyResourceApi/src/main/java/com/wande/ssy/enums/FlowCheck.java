package com.wande.ssy.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 巡检是否合格 枚举
 * @author vwFisher(422578659@qq.com)
 * 2016年11月1日下午2:22:58
 */
public enum FlowCheck {
	//巡检是否在合格范围内   1合格 0不在范围内巡检
	NOPASS(0, "不在范围内巡检"),
	PASS(1, "合格");
	
	private int value;
	private String name;
	
	private FlowCheck(int value, String name) {
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
 		for (FlowCheck s : FlowCheck.values()) {
 			if (s.getValue() == value) {
 				return s.name;
 			}
 		}
        return "";
    }
 	
 	public static List<Map<String, Object>> getSelect() {
 		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
 		for (FlowCheck s : FlowCheck.values()) {
 			Map<String,Object> m = new HashMap<String,Object>();
 			m.put("value", s.getValue());
 			m.put("name", s.getName());
 			result.add(m);
 		}
 		return result;
 	}
}
