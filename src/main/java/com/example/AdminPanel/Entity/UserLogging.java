package com.example.AdminPanel.Entity;

import com.example.AdminPanel.Models.Users;
import com.example.AdminPanel.Models.UsersActivity;
import com.example.AdminPanel.Repository.UserLoggingRepo;
import com.example.AdminPanel.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component

public class UserLogging {

    @Autowired
    UserLoggingRepo userLoggingRepo;
    @Pointcut("execution(* com.example.AdminPanel.Controller.*.*(..))")
    public void forLogging(){};
    @Before("forLogging()")
    public void beforeCall(JoinPoint joinPoint) {
        MethodSignature methodSignature =  (MethodSignature) joinPoint.getSignature();
        String url = "";
        Message message = (methodSignature.getMethod().getAnnotation(Message.class));
        String value = "";
        if(message != null) {
            value = message.value();
        }
          Object[] obj = joinPoint.getArgs();
          log.info(value);
//        String value2 = String.valueOf(methodSignature.getMethod().getAnnotation(Data.class));
          for (Object tempArg : obj) {
              if(tempArg instanceof HttpServletRequest){
                  HttpServletRequest request = (HttpServletRequest)  tempArg;
                  url = request.getServletPath();
                  value += (request.getParameter("id"));
              }
          }
          UsersActivity usersActivity = UsersActivity.builder().message(value).url(url).build();
          userLoggingRepo.save(usersActivity);

//        log.info(value + " " + value2);

    }
}

