package com.wande.ssy.enums;

/**
 */
public enum AreaType {

	INDOOR(1, "室内"),		//
	OUTDOOR(2, "室外"),		//
	PARK(3, "公园");		// 
	
	private int value;
	private String name;
	
	private AreaType(int value, String name) {
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
 		for (AreaType s : AreaType.values()) {
 			if (s.getValue() == value) {
 				return s.name;
 			}
 		}
        return "";
    }
 	
}
