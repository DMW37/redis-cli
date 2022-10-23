package com.study.springbootredis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @author: 邓明维
 * @date: 2022/10/22
 * @description:
 */
@Configuration
public class MyTemplate {
    @Bean
    public StringRedisTemplate xxoo(RedisConnectionFactory factory){
        StringRedisTemplate tp = new StringRedisTemplate(factory);
        tp.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        return tp;
    }
}
