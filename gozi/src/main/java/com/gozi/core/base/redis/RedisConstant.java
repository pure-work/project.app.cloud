package com.gozi.core.base.redis;

public class RedisConstant {

    public static final String SYS_DICT_KEYS = "sys_dict_:%s";//系统字典表字典值缓存

    public static final String SYS_CONFIG_KEYS = "sys_config_key:%s";//SYS_CONFIG_KEYS 系统参数表缓存
    
	public static final Long REDIS_DAY_SECONDS =86400L;	//一天
	
	public static final Long REDIS_HOUR_SECONDS =3600L;	//一个小时
	
	public static final Long REDIS_MINUTE_SECONDS =60L;	//1分钟
	
	public static final Long REDIS_MONTH_SECONDS = 2592000L;	//30天
	
    public static final String TOKEN_KEY = "token:%s";//登录token

    public static final String USER_KEY = "user:%s";//用户,可通过此获取到token

    public static final String PHONE_CODE_SESSION_KEY = "phone_code_session_id:%s";//发送短信验证码的sessionId

}
