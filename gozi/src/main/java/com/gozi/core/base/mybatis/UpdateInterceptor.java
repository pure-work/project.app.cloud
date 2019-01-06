package com.gozi.core.base.mybatis;

import com.gozi.core.base.entity.BaseEntity;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Properties;

/**
 * Mybatis - 更新拦截器拦截器
 * 
 * @author
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = {  MappedStatement.class, Object.class })})
@Component
public class UpdateInterceptor implements Interceptor {  
  
    @Override  
    public Object intercept(Invocation invocation) throws Throwable {  
        MappedStatement stmt = (MappedStatement) invocation.getArgs()[0];  
        Object param = invocation.getArgs()[1];  
        if (stmt == null) {  
            return invocation.proceed();  
        }
        if (stmt.getSqlCommandType().equals(SqlCommandType.INSERT)) {  
            if (param != null && param instanceof BaseEntity) {  
            	BaseEntity e = (BaseEntity) param;  
                if (e.getCreateTime() == null) {
                    e.setCreateTime(new Date());
                    e.setModifyTime(new Date());
                }
            }  
        }
        if (stmt.getSqlCommandType().equals(SqlCommandType.UPDATE)) {  
            if (param != null && param instanceof BaseEntity) {  
            	BaseEntity e = (BaseEntity) param;
                e.setModifyTime(new Date());
                e.setCreateTime(null);
            }  
        }
        return invocation.proceed();
    }  
  
    @Override  
    public Object plugin(Object target) {  
        return Plugin.wrap(target, this);  
    }  
  
    @Override  
    public void setProperties(Properties properties) {  
    }  
}  