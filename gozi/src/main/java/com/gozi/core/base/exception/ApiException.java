package com.gozi.core.base.exception;

import com.gozi.core.base.enums.ResultCode;
import lombok.Getter;

import java.text.MessageFormat;

@Getter
//@Setter
public class ApiException extends RuntimeException {
	private static final long serialVersionUID = -8547251499063503027L;
	private Integer errorCode;
	private String errorMessage;
	public ApiException(Integer errorCode, String errorMessage, Object... args) {
		super(errorCode + ":" + errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = MessageFormat.format(errorMessage, args);
		
	}
	
	public ApiException(ResultCode resultCode) {
		this(resultCode.getCode(), resultCode.getTitle());
	}

}
