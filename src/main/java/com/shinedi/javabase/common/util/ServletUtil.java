package com.shinedi.javabase.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Description
 *
 * @author hanjie
 */

public class ServletUtil {

    /**
     * 获取当前请求 request
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }


    public static Optional<HttpServletRequest> getRequest() {
        try {
            return Optional.ofNullable(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    /**
     * 获取当前请求 response
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getCurrentResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    }



    public static Optional<HttpServletResponse> getResponse() {
        try {
            return Optional.ofNullable(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
