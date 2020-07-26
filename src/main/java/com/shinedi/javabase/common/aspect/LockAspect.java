package com.shinedi.javabase.common.aspect;

import com.shinedi.javabase.common.constant.RedisLock;
import com.shinedi.javabase.common.exception.ShineLoggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(2)
public class LockAspect {
    private Logger logger = ShineLoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.shinedi.javabase.common.annotation.DistributeLock)")
    public void lockCut() {
    }

    @Around(value = "lockCut()")
    public Object doLock(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        boolean locked = RedisLock.isLocked(methodName);
        if (locked) {
            logger.info("----是否锁----",locked);
            return null;
        }

        boolean lock = RedisLock.lock(methodName);
        logger.info("----上锁----",lock);
        if (!lock) {
            return null;
        }

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            logger.error("任务异常", e);
        } finally {
            RedisLock.unlock(methodName);
            logger.info("----释放锁----",methodName);
        }

        return result;
    }
}
