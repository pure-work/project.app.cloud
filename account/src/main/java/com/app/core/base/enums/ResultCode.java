package com.app.core.base.enums;

/**
 * 接口编码
 *
 */
public enum ResultCode {

	// 成功
	SUCESS("100000", "操作成功"),
	/** token失效 **/
	RESULT100001("100001","Token失效,请重新登录"),
	// 失败
	ERROR99998("99998","{0}"),
	// 系统异常
	ERROR99999("99999","网络异常,请稍后再试");
	
	
	private String code;

	private String title;

	private ResultCode(String code, String title) {
		this.code = code;
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
