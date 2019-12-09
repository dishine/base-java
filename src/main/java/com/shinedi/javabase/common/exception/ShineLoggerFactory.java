package com.shinedi.javabase.common.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author D-S
 * Created on 2019/12/9 11:08 上午
 */
public class ShineLoggerFactory {

    private static Logger logger = ShineLoggerFactory.getLogger(LoggerFactory.class);

    private static Set<LogSubscriber> subscribers = new HashSet<>();

    public static Logger getLogger(Class<?> clazz) {
        Logger logger = org.slf4j.LoggerFactory.getLogger(clazz);
        return new ShineLogger(logger, subscribers);
    }

    /**
     * 注册异常监听
     */
    public synchronized static void register(LogSubscriber subscriber) {
        if (subscribers.add(subscriber)) {
            logger.info("加入新的异常消息监听：" + subscriber.getClass().getName());
        }
    }
}
