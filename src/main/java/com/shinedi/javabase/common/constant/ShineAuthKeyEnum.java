package com.shinedi.javabase.common.constant;

import com.shinedi.javabase.common.util.TextUtils;

/**
 * @author D-S
 * Created on 2019/12/9 11:40 上午
 */
public enum ShineAuthKeyEnum {

    WIND_BASE("31040481c9dd4d936986cd214f181a61"),


    /**
     * 钱包
     */
    WIND_WALLET("41040471c9dd4d096986cd214f181b87");

    private String key;

    ShineAuthKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static ShineAuthKeyEnum get(String key) {
        for (ShineAuthKeyEnum windAuthKeyEnum : ShineAuthKeyEnum.values()) {
            if (TextUtils.equals(windAuthKeyEnum.getKey(), key)) {
                return windAuthKeyEnum;
            }
        }
        return null;
    }


}
