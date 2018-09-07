package com.wande.ssy.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 器材标准 枚举
 * @author vwFisher(422578659@qq.com)
 * 2016年11月11日下午10:42:32
 */
public enum Standardcode {
	// 器材标准:0表示无,1无标准 2老国际 3新国际
	NOSTANDARD(1, "无标准"),	// 1无标准
	OLDNATION(2, "老国际"),	// 2老国际
	NEWNATION(3, "新国际");	// 3新国际
	
	private int value;
	private String name;
	
	private Standardcode(int value, String name) {
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
 		for (Standardcode s : Standardcode.values()) {
 			if (s.getValue() == value) {
 				return s.name;
 			}
 		}
        return "";
    }
 	
 	public static List<Map<String, Object>> getSelect() {
 		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
 		for (Standardcode s : Standardcode.values()) {
 			Map<String,Object> m = new HashMap<String,Object>();
 			m.put("standardcode", s.getValue());
 			m.put("standardcodeName", s.getName());
 			result.add(m);
 		}
 		return result;
 	}
}
