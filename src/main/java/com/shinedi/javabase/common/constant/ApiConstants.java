package com.shinedi.javabase.common.constant;

import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Description
 *
 * @author hanjie
 */
@Configuration
public class ApiConstants {


    public static String REDIS_LOCK_KEY_WALLET = "key_redis_lock_worker_";

    /**
     * request中提取user信息的key
     */
    public final static String USER_KEY = "user_key";

    /**
     * request中提取其他项目的来源值
     */
    public final static String AUTH_FROM = "auth_from";



//    /**
//     * 注入域名
//     */
//    @Value("${mrwind.baseUrl.message}")
//    public void setBaseUrl(String baseUrl) {
//        // 初始化风信聊天&推送包 url
//        messageHelperConfig.setWindBaseUrl(baseUrl);
//    }
}
