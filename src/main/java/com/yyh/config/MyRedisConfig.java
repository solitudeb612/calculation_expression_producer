package com.yyh.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
@Configuration
public class MyRedisConfig {
    @Resource
    private RedisConnectionFactory factory;

    @Bean
    public RedisTemplate redisTemplate(){
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        redisTemplate.setKeySerializer(redisTemplate.getStringSerializer());
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        redisTemplate.setValueSerializer(serializer);

        //解决查询缓存转换异常的问题(序列化问题)
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        om.setTimeZone(TimeZone.getDefault());
        om.configure(MapperFeature.USE_ANNOTATIONS, false);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance ,ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        serializer.setObjectMapper(om);

        return redisTemplate;
    }
}

/*
   将Java对象序列化为JSON格式并存储到Redis中的RedisTemplate
   配置包括了序列化和反序列化的各种参数，以确保在数据存储和检索过程中能够正确地进行对象转换
 */
