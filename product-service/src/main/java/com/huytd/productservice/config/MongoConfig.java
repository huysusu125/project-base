package com.huytd.productservice.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Configuration
@RequiredArgsConstructor
public class MongoConfig {
    @Value("${spring.data.mongodb.database}")
    String databaseName;

    private final MappingMongoConverter mappingMongoConverter;

    // remove _class
    @PostConstruct
    public void setUpMongoEscapeCharacterConversion() {
        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    }

}