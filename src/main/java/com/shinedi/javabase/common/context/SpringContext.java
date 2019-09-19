package com.shinedi.javabase.common.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;


@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static Optional<HttpServletRequest> getRequest() {
        try {
            return Optional.ofNullable(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static Optional<HttpServletResponse> getResponse() {
        try {
            return Optional.ofNullable(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static ThreadLocal<CurrentContext> threadLocal = new NamedThreadLocal<>("CurrentContext");


    public static void setContext(CurrentContext currentContext) {
        threadLocal.set(currentContext);
    }

    public static Date getDate() {
        CurrentContext currentContext = threadLocal.get();
        if (currentContext == null) {
            return new Date();
        } else {
            return currentContext.getCurrentDate();
        }
    }

    public static Timestamp getTimestamp() {
        CurrentContext currentContext = threadLocal.get();
        if (currentContext == null) {
            return new Timestamp(System.currentTimeMillis());
        } else {
            return currentContext.getCurrentTimestamp();
        }
    }


    public static void removeContext() {
        threadLocal.remove();
    }
}
