package com.shinedi.javabase.common.aspect;


import com.alibaba.fastjson.JSON;
import com.shinedi.javabase.common.context.SpringContext;
import com.shinedi.javabase.common.error.BusinessException;
import com.shinedi.javabase.common.error.EmBusinessError;
import com.shinedi.javabase.common.util.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(0)
public class LogAndExpceptionAspect {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.shinedi.javabase.controller.*.*(..))")
    public void pointCut(){}

    @Around("pointCut()")
    public Object doAspect(ProceedingJoinPoint proceedingJoinPoint){
        Object resulet;
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        StringBuilder paramBuilder = new StringBuilder();
        for (int i =0;i<proceedingJoinPoint.getArgs().length;i++){
            if (proceedingJoinPoint.getArgs()[i]!=null){
                String name = proceedingJoinPoint.getArgs()[i].getClass().getName();
                if (name.contains("org.apache.catalina.connector")
                        || "org.springframework.web.multipart.commons.CommonsMultipartFile".equals(name)
                        || name.contains("org.springframework.validation")) {
                    continue;
                }
                paramBuilder.append(JSON.toJSONString(proceedingJoinPoint.getArgs()[i]));
            }
        }
        logger.info("-----收到请求----->"+methodName);
        logger.info("-----收到请求参数----->"+paramBuilder.toString());
        Object result;
        long startTimeMillis = System.currentTimeMillis();
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            if (throwable instanceof BusinessException) {
                //强转为自定义类型
                BusinessException businessException = (BusinessException) throwable;
                //调用自定义的统一数据json格式返回
                result = Result.getFail(businessException.getErrCode() +":"+ businessException.getErrMsg());
//                result= Result.getFail(throwable.toString());
            } else {
                //从枚举类中取出自定义的错误码和错误信息
                result = Result.getFail(EmBusinessError.KNOWN_ERROR.getErrCode() +":"+ throwable.getMessage() + throwable.toString());
            }
            SpringContext.getResponse().ifPresent(response -> response.setStatus(HttpStatus.OK.value()));
//            logger.error(JSON.toJSONString(workerActionLog), throwable);
            logger.error(throwable.getMessage(),throwable);
        }
        Long spendTime = System.currentTimeMillis() - startTimeMillis;
        logger.info("-----请求结束-----耗时--->"+spendTime+"ms");
        logger.info("-----返回数据----->"+JSON.toJSONString(result));
        return result;
    }
}


