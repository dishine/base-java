package com.shinedi.javabase.common.aspect;

import com.shinedi.javabase.common.annotation.SkipShineAuthorization;
import com.shinedi.javabase.common.annotation.SkipTokenAuthorization;
import com.shinedi.javabase.common.constant.ApiConstants;
import com.shinedi.javabase.common.constant.ShineAuthKeyEnum;
import com.shinedi.javabase.common.util.JwtUtil;
import com.shinedi.javabase.common.util.Result;
import com.shinedi.javabase.common.util.ServletUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * token 校验切面
 *
 * @author hanjie
 */
@Component
@Aspect
@Order(1)
public class AuthorizationAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(public * com.shinedi.javabase.controller..*.*(..))")
    public void requestPointCut() {

    }

    @Around("requestPointCut()")
    public Object doAuthorization(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean isFromWindAuth = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(SkipShineAuthorization.class) != null;
        boolean tokenAuth = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(SkipTokenAuthorization.class) != null;


        HttpServletRequest request = ServletUtil.getCurrentRequest();

        if (!isFromWindAuth) {
            String key = request.getHeader("key");
            ShineAuthKeyEnum from = ShineAuthKeyEnum.get(key);
            if (from == null) {
                // 第三方携带的 key 无效
                return Result.getFail("error.permission.denied");
            } else {
                // 把来源值放在 request 中
                request.setAttribute(ApiConstants.AUTH_FROM, from);
            }
        } else if (!tokenAuth) {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            try {
                String[] userIdAndTel = JwtUtil.parseJWT(token).getSubject().split("_");
                String userId = userIdAndTel[0];
                String tel = userIdAndTel[1];
                if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(tel)) {
                    request.setAttribute(ApiConstants.USER_KEY, userId);
                } else {
                    // throw new RuntimeException();
                    return Result.getFail("token error");
                }
            } catch (Exception e) {
                //throw new RuntimeException();jia
                return Result.getFail("token error");
            }
        }

        return joinPoint.proceed();
    }

}
