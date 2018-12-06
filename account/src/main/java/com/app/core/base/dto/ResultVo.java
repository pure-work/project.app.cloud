package com.app.core.base.dto;

import com.app.core.base.enums.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * 接口结构
 */
@ApiModel("API接口返回实体")
@Getter
@Setter
public class ResultVo<T> implements Serializable {

	private static final long serialVersionUID = -5159428286666775049L;
	/** 内容 */
	@ApiModelProperty(value="内容", required = true)
	private T content;
	/** 编码 */
	@ApiModelProperty(value="返回码", required = true)
	private String code;
	/** 描述 */
	@ApiModelProperty(value="描述", required = true)
	private String desc;
	/**
	 * 初始化一个新创建的 Message 对象，使其表示一个空消息。
	 */
	public ResultVo() { }
	/**
	 * 初始化一个新创建的 Message 对象
	 * @param content
	 *            内容
	 */
	public ResultVo(String code, String desc, T content, Object... args) {
		this.code = code;
		this.desc = MessageFormat.format(desc, args);
		this.content = content;
	}
	/**
	 * 返回成功消息
	 */
	public static ResultVo success(Object content, Object... args) {
		return new ResultVo(ResultCode.SUCESS.getCode(), ResultCode.SUCESS.getTitle(), content, args);
	}
	/**
	 * 返回失败消息
	 */
	public static ResultVo error(String code,String title, Object... args) {
		return new ResultVo(code, title, "", args);
	}
	/**
	 * 返回失败消息
	 */
	public static ResultVo error(ResultCode resultCode, Object... args) {
		return new ResultVo(resultCode.getCode(), resultCode.getTitle(), "", args);
	}
	/**
	 * 获取内容
	 * @return 内容
	 */
	public T getContent() {
		return content;
	}
	/**
	 * 设置内容
	 * @param content
	 *            内容
	 */
	public void setContent(T content) {
		this.content = content;
	}
	@Override
	public String toString() {
		if (content != null ){
			return content.toString();//MessageUtils.getMessage((String) content);
		}
		else {
			return "";
		}
	}
	
}