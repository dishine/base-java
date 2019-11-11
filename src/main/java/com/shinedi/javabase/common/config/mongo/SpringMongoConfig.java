package com.shinedi.javabase.common.config.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringMongoConfig {

    @Value("${mrwind.mongo.host}")
    private String[] serverStrings;

    @Value("${mrwind.mongo.user}")
    private String username;

    @Value("${mrwind.mongo.pwd}")
    private String password;

    @Value("${mrwind.mongo.authDb}")
    private String authDb;

    @Value("${mrwind.mongo.dbName}")
    private String dbName;
//
//    @Value("${mrwind.mongo.readsec}")
//    private boolean readSecondary;

    @Bean("mongoClient")
    public MongoFactoryBean mongoClient() {
        MongoFactoryBean factoryBean = new MongoFactoryBean();
        factoryBean.setServerStrings(serverStrings);
        factoryBean.setAuthDb(authDb);
        factoryBean.setUsername(username);
        factoryBean.setPassword(password);
//        factoryBean.setReadSecondary(readSecondary);

        return factoryBean;
    }

    @Bean
    public MorphiaFactoryBean morphiaFactoryBean() {
        MorphiaFactoryBean morphiaFactoryBean = new MorphiaFactoryBean();
        String[] arrays = new String[1];
        arrays[0] = "com.mrwind.delivery.model";

        morphiaFactoryBean.setMapPackages(arrays);
        morphiaFactoryBean.setTypeConverter(new BigDecimalConverter());

        return morphiaFactoryBean;
    }

    @Bean("call")
    public DatastoreFactoryBean dataBean(@Autowired MongoFactoryBean mongoFactoryBean,
                                            @Autowired MorphiaFactoryBean morphiaFactoryBean) {
        return createMongoBean(mongoFactoryBean,morphiaFactoryBean,dbName);
    }

    private DatastoreFactoryBean createMongoBean(MongoFactoryBean mongoFactoryBean,
                                                 MorphiaFactoryBean morphiaFactoryBean,
                                                 String dbName) {
        DatastoreFactoryBean factoryBean = new DatastoreFactoryBean();
        try {
            factoryBean.setMorphia(morphiaFactoryBean.getObject());
            factoryBean.setMongo(mongoFactoryBean.getObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        factoryBean.setDbName(dbName);
        factoryBean.setToEnsureCaps(false);

        return factoryBean;
    }

}
