package com.example.AdminPanel.Entity;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
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
        String value = String.valueOf(methodSignature.getMethod().getAnnotation(Message.class));
          Object[] obj = joinPoint.getArgs();
          log.info(value);
//        String value2 = String.valueOf(methodSignature.getMethod().getAnnotation(Data.class));
          for (Object tempArg : obj) {
              if(tempArg instanceof HttpServletRequest){
                  HttpServletRequest request = (HttpServletRequest)  tempArg;
                  log.info("\u001B[33m" + "id = " +request.getParameter("id")  +"\u001B[0m");
              }
          }
//        log.info(value + " " + value2);

    }
}

