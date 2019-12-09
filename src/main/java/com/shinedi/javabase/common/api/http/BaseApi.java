package com.shinedi.javabase.common.api.http;

import com.alibaba.fastjson.JSONObject;
import com.shinedi.javabase.common.exception.ShineLoggerFactory;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public class BaseApi {

    protected static Client client;

    protected Logger logger = ShineLoggerFactory.getLogger(getClass());



    static {
        client = Client.create();
        client.setConnectTimeout(3000);
      //  client.setReadTimeout(10000);
    }

    /**
     * 从response获取结果实体类
     */
    <T> Optional<T> getObjectFromResponse(Class<T> clazz, ClientResponse response) {
        if (response.getStatus() == HttpStatus.OK.value()) {
            String entity = response.getEntity(String.class);

            logger.info(entity);

            try {
                JSONObject jsonObject = JSONObject.parseObject(entity);
                if ("0".equals(jsonObject.getString("code"))) {
                    logger.info("解析成功");
                    return Optional.ofNullable(jsonObject.getObject("data", clazz));
                }
            } catch (Exception e) {
                response.close();
                logger.error("解析返回结果失败: " + entity);
            }
        }
        return Optional.empty();
    }

//    <T> List<T> getObjectFromResponse(Class<T> clazz, ClientResponse response) {
//        if (response.getStatus() == HttpStatus.OK.value()) {
//            String entity = response.getEntity(String.class);
//            logger.info(entity);
//            try {
//                JSONObject jsonObject = JSONObject.parseObject(entity);
//                if ("0".equals(jsonObject.getString("code"))) {
//                    if (jsonObject.getJSONArray("data") == null || jsonObject.getJSONArray("data").isEmpty()) {
//                        return new ArrayList<>();
//                    }
//                    return jsonObject.getJSONArray("data").toJavaList(clazz);
//                }
//            } catch (Exception e) {
//                response.close();
//                logger.error("解析返回结果丢失: " + entity, e);
//            }
//        }
//        return new ArrayList<>();
//
//    }
        /**
         * 从response获取结果实体类
         */
   <T> List<T> getArrayFromResponse(Class<T> clazz, ClientResponse response) {
               if (response.getStatus() == HttpStatus.OK.value()) {
            String entity = response.getEntity(String.class);
            logger.info(entity);
            try {
                JSONObject jsonObject = JSONObject.parseObject(entity);
                if ("0".equals(jsonObject.getString("code"))) {
                    if (jsonObject.getJSONArray("data") == null || jsonObject.getJSONArray("data").isEmpty()) {
                        return new ArrayList<>();
                    }
                    return jsonObject.getJSONArray("data").toJavaList(clazz);
                }
            } catch (Exception e) {
                response.close();
                logger.error("解析返回结果丢失: " + entity, e);
            }
        }
        return new ArrayList<>();
    }
}
