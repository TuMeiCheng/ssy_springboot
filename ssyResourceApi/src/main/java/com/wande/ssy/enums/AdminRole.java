package com.wande.ssy.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户角色枚举
 * @author vwFisher(422578659@qq.com)
 * 2017年1月13日上午10:32:47
 */
public enum AdminRole {
	//1管理员 2管理公司 3 体育局
	ADMIN(1, "管理员"),
	AGENCY(2, "管理公司"),
	ORG(3, "体育局");
	
	private Integer value;
	private String name;
	
	private AdminRole(Integer value, String name) {
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
 		for (AdminRole s : AdminRole.values()) {
 			if (s.getValue() == value) {
 				return s.name;
 			}
 		}
        return "";
    }
 	
 	public static List<Map<String, Object>> getSelect() {
 		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
 		for (AdminRole s : AdminRole.values()) {
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("roleId", s.getValue());
			m.put("roleName", s.getName());
			result.add(m);
 		}
 		return result;
 	}
 	
 	/**
 	 * 去除超级管理员(ADMIN)的下拉选
 	 * @return
 	 */
 	public static List<Map<String, Object>> getSelectExt() {
 		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
 		for (AdminRole s : AdminRole.values()) {
 				if (s.getValue() != ADMIN.getValue()) {
 				Map<String,Object> m = new HashMap<String,Object>();
 				m.put("roleId", s.getValue());
 				m.put("roleName", s.getName());
 				result.add(m);
 			}
 		}
 		return result;
 	}
 	
 	public static boolean isExist(int value){
 		for (AdminRole s : AdminRole.values()) {
 			if (s.getValue() == value) {
 				return true;
 			}
 		}
        return false;
 	}
}
