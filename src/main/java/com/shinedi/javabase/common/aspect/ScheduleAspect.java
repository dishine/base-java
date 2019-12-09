package com.shinedi.javabase.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author wuyiming
 */
@Component
@Aspect
@Order(3)
public class ScheduleAspect {

    //从配置中判断是否需要 进行定时任务
    @Value("${mrwind.schedule.disabled}")
    private String[] disabled = {};

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void scheduleCut() {
    }

    @Around(value = "scheduleCut()")
    public Object doLogController(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        if (disabled.length > 0) {
            String first = disabled[0];
            // 设定 * 为禁止所有方法执行
            if ("*".equals(first)) {
                return null;
            }
            if (Arrays.asList(disabled).contains(methodName)) {
                return null;
            }
        }

        return joinPoint.proceed();
    }

}
