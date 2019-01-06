package com.gozi.core.base.exception;

import com.gozi.core.base.enums.BusinessExceptionCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description 业务异常的封装
 */
@Getter
@Setter
public class BusinessException extends RuntimeException{
	//友好提示的code码
	private int errorCode;
	//友好提示
	private String errorMessage;
	//业务异常跳转的页面
	private String redirectUrl;

	public BusinessException(BusinessExceptionCode businessExceptionCode){
		this.errorCode = businessExceptionCode.getErrorCode();
		this.errorMessage = businessExceptionCode.getErrorMessage();
		this.redirectUrl = businessExceptionCode.getRedirectUrl();
	}
	public BusinessException(Exception e){
		super(e);
	}
	public BusinessException(String message){
		super(message);
	}
}
