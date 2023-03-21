package com.huytd.basecacheredis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;


@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    @Value("${spring.data.redis.host:localhost}")
    private String host;

    @Value("${spring.data.redis.port:6379}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
		/*
		In case of using Lettuce, do not validate connection when to connect to pool.
		 Otherwise it will connect to pool first and then to cluster nodes one by one,
		 then fetches the real data. Or sometimes you will see the following Exception case:
				Redis exception; nested exception is io.lettuce.core.RedisException: Connection is closed
		* */
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(host, port);
        connectionFactory.setValidateConnection(false);
        return connectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.deactivateDefaultTyping();
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);
        redisTemplate.setDefaultSerializer(serializer);
        return redisTemplate;
    }

}
