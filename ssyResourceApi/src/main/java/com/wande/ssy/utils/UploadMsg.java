package com.wande.ssy.utils;

/**
 * 返回上传的结果
 * 可以封装错误码的运行时异常
 * 
 * @author vwFisher(422578659@qq.com)
 * 2017年2月8日 下午5:13:07
 */
@SuppressWarnings("serial")
public class UploadMsg extends RuntimeException {
	private int errCode;
	private String errMsg;
	private Object obj;
	
	public UploadMsg(int errCode, String errMsg, Object obj) {
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.obj = obj;
	}
	
	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
