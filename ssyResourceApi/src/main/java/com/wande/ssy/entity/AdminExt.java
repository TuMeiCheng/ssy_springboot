package com.wande.ssy.entity;

/**
 * Admin扩展类(重要信息保护)
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年1月13日上午10:13:01
 */
public class AdminExt extends Admin {

	private static final long serialVersionUID = 5540659628778049197L;
	
	private String pwd; // 密码
	private int skey; // 随机因子

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getSkey() {
		return skey;
	}

	public void setSkey(int skey) {
		this.skey = skey;
	}

}