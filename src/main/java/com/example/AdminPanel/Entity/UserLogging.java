package com.example.AdminPanel.Entity;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class UserLogging {

    @Pointcut("execution(* com.example.AdminPanel.Controller.*.*(..))")
    public void forLogging(){};
    @Before("forLogging()")
    public void beforeCall(JoinPoint joinPoint) {
        MethodSignature methodSignature =  (MethodSignature) joinPoint.getSignature();
        String value = methodSignature.getMethod().getAnnotation(Message.class).value();

    }
}

