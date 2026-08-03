package com.ait.web.util;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;

/**
 * 
 * @author wwhhf
 * @since 2016年5月20日
 * @comment 异常通知消息，将一般运行时异常封装为业务运行异常
 */
public class ExceptionAdvisor implements ThrowsAdvice {

    /**
     * 
     * @author wwhhf
     * @since 2016年5月20日
     * @comment 一般运行异常抛出之后的处理
     * @param method
     * @param args
     * @param target
     * @param ex
     * @throws Throwable
     */
    public void afterThrowing(Method method, Object[] args, Object target,
            Exception ex) throws Throwable {

        String methodInfo=method.getName();

        Logger logger = Logger.getLogger(target.getClass());
        logger.error("**************************************************************");
        logger.error("method happen: " + methodInfo);
        logger.error("Exception class: " + ex.getClass().getName());
        logger.error("ex.getMessage():" + ex.getMessage());
        logger.error("**************************************************************");
    }
}