package com.gec.hawsteproject.hawaste.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.time.LocalDateTime;

@Configuration
public class RedisConfig {

    /**
     * 自定义RedisTemplate
     * redisConnectionFactory:由springboot读取配置，自动创建的连接工厂对象
     * @return
     */
    @Bean("redisTemplate")  //指定bean的名字，springboot就不会自动创建RedisTemplate<Object,Object>
    public RedisTemplate<String,Object> getRedisTemplate(RedisConnectionFactory redisConnectionFactory, LocalDateTimeSerializer timeSerializer, LocalDateTimeDeserializer timeDeserializer){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //设置自定义的ObjectMapper，并且设置自定义序列化和反序列化LocalDateTime的序列化器
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class,timeSerializer);//设置序列化LocalDateTime的序列化器
        timeModule.addDeserializer(LocalDateTime.class,timeDeserializer);
        mapper.registerModule(timeModule);
        //设置生成json字符串的时候，添加类型字符串描述
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(),ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        GenericJackson2JsonRedisSerializer redisSerializer = new GenericJackson2JsonRedisSerializer(mapper);

        /*
        * 设置自定义的序列化器
        * */
        redisTemplate.setDefaultSerializer(redisSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
