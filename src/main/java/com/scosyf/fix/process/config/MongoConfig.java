package com.scosyf.fix.process.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;

/**
 * @program: bucks
 * @description:
 * @author: kunbu
 * @create: 2019-08-26 16:38
 **/
@Configuration
public class MongoConfig {

    /**
     * 去除spring-dada-mongo自动生成的_class字段
     * https://www.cnblogs.com/keeya/p/9969535.html
     * https://blog.csdn.net/asahinokawa/article/details/83894670
     *
     * @param factory
     * @param context
     * @param beanFactory
     * @return
     * @author kunbu
     * @time 2019/8/26 16:45
     **/
    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory, MongoMappingContext context, BeanFactory beanFactory) {

        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, context);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return converter;
    }

    /**
     * 配置自定义mongo中document和代码中模型的转换（参考Spring全家桶）
     *
     * @param
     * @return
     * @author kunbu
     * @time 2019/8/26 16:45
     **/
    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(new ArrayList<>());
    }

}
