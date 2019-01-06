package com.gozi.core.base.enums;

import lombok.Getter;

/**
 * 接口编码
 *
 */
@Getter
public enum ResultCode {
	// 成功
	SUCCESS(100000, "操作成功"),
	/** token失效 **/
	TOKEN_EXPIRED(100001,"Token失效,请重新登录"),
	// 失败
	FAILED(99998,"{0}"),
	// 不支持的操作类型
	NOT_SUPPORT(99997,"不支持的操作类型"),
	// 系统异常
	ERROR(99999,"网络异常,请稍后再试");
	private Integer code;
	private String title;
	ResultCode(Integer code, String title) {
		this.code = code;
		this.title = title;
	}
}
