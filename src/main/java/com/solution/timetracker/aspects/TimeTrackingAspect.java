package com.solution.timetracker.aspects;

import com.solution.timetracker.event.ExecutionTimeEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Aspect
@Component
@AllArgsConstructor
@Slf4j
public class TimeTrackingAspect {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Pointcut("@annotation(com.solution.timetracker.annotations.TrackTime)")
    public void timeTrackingPointCut() {
    }

    @Around("timeTrackingPointCut()")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        log.info("Выполнение метода {} с параметрами {}", methodName, methodArgs);
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        long timeTaken = System.nanoTime() - startTime;

        applicationEventPublisher.publishEvent(new ExecutionTimeEvent(this, methodName, methodArgs, timeTaken));

        log.info("Метод {} с параметрами {} выполнился за {} нс c результатом {}", methodName, methodArgs,
                timeTaken, result);

        return result;
    }


    @Pointcut("@annotation(com.solution.timetracker.annotations.TrackAsyncTime)")
    public void timeTrackingAsyncPointCut() {}

    @Around("timeTrackingAsyncPointCut()")
    public Object trackAsyncTime(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        if (result instanceof CompletableFuture<?>) {
            CompletableFuture<?> future = (CompletableFuture<?>) result;
            future.whenComplete((res, ex) -> {
                long takenTime = System.nanoTime() - startTime;
                if (ex != null) {
                    log.error("Метод {} с параметрами {} завершился с ошибкой {} за {} нс",
                            methodName, methodArgs, ex, takenTime);
                } else {
                    log.info("Метод {} с параметрами {} выполнился асинхронно за {} нс с результатом {}",
                            methodName, methodArgs, takenTime, res);
                }
                applicationEventPublisher.publishEvent(new ExecutionTimeEvent(this,
                        methodName, methodArgs, takenTime));
            });
        }
        return result;
    }
}

