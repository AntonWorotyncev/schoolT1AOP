package com.solution.timetracker.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
@Order(1)
public class ExceptionHandlerAspect {
    @AfterThrowing(pointcut = "within(com.solution.timetracker.service.impl.*) && " +
            "execution(public java.util.List * (..)) throws @com.solution.timetracker.annotations.Trows *)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("Произошла ошибка {} при вызове метода: {}", ex, joinPoint.getSignature().toShortString());
    }
}

