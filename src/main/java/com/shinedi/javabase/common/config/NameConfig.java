package com.shinedi.javabase.common.config;//package com.mrwind.wallet.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
//@RefreshScope
public class NameConfig {

    public String getWINDBASE() {
        return WINDBASE;
    }

    public void setWINDBASE(String WINDBASE) {
        this.WINDBASE = WINDBASE;
    }

    /**
     * 从配置文件中获取内容
     * @param name name
     */
    @Value("${cloudname.team}")
    private void setName(String name){
        WINDBASE = name;
    }

    public String WINDBASE;
}
