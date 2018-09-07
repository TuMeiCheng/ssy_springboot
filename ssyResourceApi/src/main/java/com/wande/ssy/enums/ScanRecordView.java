package com.wande.ssy.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 市民扫描二维码,获取器材信息, 看器材健身视频,次数记录
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月16日上午9:36:53
 */
public enum ScanRecordView {
	//页面数据  1查看器材数据  2查看器材视频
	INFO(1, "信息"),		// 1查看器材数据 
	VIDEO(2, "视频");		// 2查看器材视频
	
	private int value;
	private String name;
	
	private ScanRecordView(int value, String name) {
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
 	
 	public static String getName(int value) {
 		for (ScanRecordView s : ScanRecordView.values()) {
 			if (s.getValue() == value) {
 				return s.name;
 			}
 		}
        return "";
    }
 	
 	public static List<Map<String, Object>> getValueList() {
 		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
 		for (ScanRecordView s : ScanRecordView.values()) {
 			Map<String,Object> m = new HashMap<String,Object>();
 			m.put("value", s.getValue());
 			m.put("sourcefrom", s.getName());
 			result.add(m);
 		}
 		return result;
 	}
}
