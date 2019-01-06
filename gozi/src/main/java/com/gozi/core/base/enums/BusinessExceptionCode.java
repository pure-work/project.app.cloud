package com.gozi.core.base.enums;

import lombok.Getter;
/**
 * @Description 所有业务异常的枚举
 */
@Getter
public enum BusinessExceptionCode {
	/**
	 * 字典
	 */
	DICT_EXISTED(400,"字典已经存在"),
	ERROR_CREATE_DICT(500,"创建字典失败"),
	ERROR_WRAPPER_FIELD(500,"包装字典属性失败"),
	/**
	 * 文件上传
	 */
	FILE_READING_ERROR(400,"FILE_READING_ERROR!"),
	FILE_NOT_FOUND(400,"FILE_NOT_FOUND!"),
	UPLOAD_ERROR(500,"上传图片出错"),
	/**
	 * 权限和数据问题
	 */
	DB_RESOURCE_NULL(400,"数据库中没有该资源"),
	NO_PERMITION(405, "权限异常"),
	REQUEST_INVALIDATE(400,"请求数据格式不正确"),
	INVALID_KAPTCHA(400,"验证码不正确"),
	CANT_DELETE_ADMIN(600,"不能删除超级管理员"),
	CANT_FREEZE_ADMIN(600,"不能冻结超级管理员"),
	CANT_CHANGE_ADMIN(600,"不能修改超级管理员角色"),
	/**
	 * 账户问题
	 */
	USER_ALREADY_REG(401,"该用户已经注册"),
	NO_THIS_USER(400,"没有此用户"),
	USER_NOT_EXISTED(400, "没有此用户"),
	ACCOUNT_FREEZED(401, "账号被冻结"),
	OLD_PWD_NOT_RIGHT(402, "原密码不正确"),
	TWO_PWD_NOT_MATCH(405, "两次输入密码不一致"),
	PHONE_ALREADY_REG(406,"该手机号码已经注册"),
	/**
	 * 错误的请求
	 */
	REQUEST_NULL(400, "请求有错误"),
	SESSION_TIMEOUT(400, "会话超时"),
	SERVER_ERROR(500, "服务器异常"),
	/**
	 * 业务特殊处理
	 */
	CHANNEL_ENABLE_MER(601, "请先把关联的商户默认通道取消,再进行禁用操作!"),
	CHANNEL_ENABLE_ROUTE(602, "有关联的路由规则,是否一并禁用？"),
	ACTIVETE_USER_EXIST(603,"活动已有用户报名无法删除！"),
	INSURANCE_REMOVE(604,"保险抢购活动已经开始无法删除！"),
	INSURANCE_STARTED(605,"保险抢购活动已经开始不能重复操作！"),
	INSURANCE_UPDATE(606,"保险抢购活动已经开始不能修改！"),
	EXTENSION_ACTIVITY_REMOVE(607,"任务已经分享，不能删除！");
	BusinessExceptionCode(Integer errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	BusinessExceptionCode(Integer errorCode, String errorMessage, String redirectUrl) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.redirectUrl = redirectUrl;
	}
	private Integer errorCode;
	private String errorMessage;
	private String redirectUrl;

}
