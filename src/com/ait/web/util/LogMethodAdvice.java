package com.ait.web.util;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

/**
 * 
 * @author wwhhf
 * @since 2016年5月20日
 * @comment 方法执行前和方法执行后的结果的日志记录
 */
public class LogMethodAdvice implements MethodBeforeAdvice,
        AfterReturningAdvice {

    /**
     * 
     * @author wwhhf
     * @since 2016年5月20日
     * @comment 拼装字符串
     * @param clazz
     * @param method
     * @param args
     * @return
     */
    private String getMethodInfo(Class clazz, Method method, Object[] args) {
        StringBuffer sb = new StringBuffer(clazz.getName() + " "
                + method.getName()).append("(");
        for (int i = 0, len = args.length; i < len; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(args[i]);
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public void afterReturning(Object returnValue, Method method,
            Object[] args, Object target) throws Throwable {
        Logger logger = Logger.getLogger(target.getClass());
        String methodInfo=getMethodInfo(target.getClass(), method, args);
        logger.info(methodInfo+" result is : " + returnValue);
        logger.info("======================");
    }

    @Override
    public void before(Method method, Object[] args, Object target)
            throws Throwable {
        Logger logger = Logger.getLogger(target.getClass());
        logger.info("======================");
        String methodInfo=getMethodInfo(target.getClass(), method, args);
        logger.info(methodInfo+" is running");
    }

}