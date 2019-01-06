package com.gozi.core.base.redis;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

public class StringKeyGenerator implements KeyGenerator {
    public static final String NO_PARAM_KEY = "$all";
   @Override
   public Object generate(Object target, Method method, Object... params) {
       if (params.length == 0) {
           return NO_PARAM_KEY;
       }
       StringBuffer key=new StringBuffer();
       for(int i=0;i<method.getParameterTypes().length;i++){
           Class<?> clazz=method.getParameterTypes()[i];
           if(!String.class.equals(clazz)&&!ClassUtils.isPrimitiveOrWrapper(clazz)){
               throw new RuntimeException("method:"+method+" @Cache* must set key with Parameter:"+clazz);
           }
           key.append(params[i]);
           if(i!=params.length-1){
               key.append("$$");
           }
       }
       return key.toString();
       
   }

}
