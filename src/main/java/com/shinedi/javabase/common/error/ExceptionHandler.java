package com.shinedi.javabase.common.error;

import com.shinedi.javabase.common.util.LocaleType;
import com.shinedi.javabase.common.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static org.slf4j.LoggerFactory.getLogger;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    @Value("${mrwind.env}")
    private String env;

    private static Logger logger = getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result handlerBusinessException(HttpServletRequest httpServletRequest, BusinessException b) {
        logger.error("err", b);
        return Result.getFail(b.getErrMsg() , b.getErrCode());
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handlerException(HttpServletRequest httpServletRequest, Exception e) {
        logger.error("err", e);
        String errMsgKey = EmBusinessError.KNOWN_ERROR.getErrMsg();
        String errMsg = LocaleType.getMessage(errMsgKey);
        // fixme  环境变量
        if (env.equals("master")) {
            return Result.getFailI18N(errMsg + e.getMessage());
        }
        return Result.getFailI18N(errMsg);
    }
}
