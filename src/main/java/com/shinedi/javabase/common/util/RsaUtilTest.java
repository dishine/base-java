package com.shinedi.javabase.common.util;


import com.shinedi.javabase.common.util.RsaUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: D-S
 * @Date: 2020/7/26 6:25 下午
 */
public class RsaUtilTest {

    public static void main(String[] args) {
        String key = "";
        String baseUrl = "https://openapi.dawnbyte.com";
        String url = "https://openapi.dawnbyte.com/api/league";
        Map<String, Object> data = new HashMap();
        data.put("game_id", 3);
        data.put("limit", 50);
        String result = "";

        try {
            result = RsaUtil.doGet(url, data, "");
        } catch (Exception var7) {
            System.out.println(var7.toString());
        }

        System.out.println(result);
    }
}
