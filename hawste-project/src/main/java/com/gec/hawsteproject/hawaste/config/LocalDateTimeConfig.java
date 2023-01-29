package com.gec.hawsteproject.hawaste.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class LocalDateTimeConfig {

    /**
     * 从配置文件中注入配置属性，可以让Date和LocalDate统一json格式
     */
    @Value("${spring.jackson.date-format}")
    private String pattern;


    /**
     * 设置LocalDateTime的序列化器
     * @return
     */

    @Bean
    public LocalDateTimeSerializer getLocalDateTimeSerializer(){
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 设置LocalDateTime反序列化器
     * @return
     */
    @Bean
    public LocalDateTimeDeserializer getLocalDateTimeDeserializer(){
        return new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 设置默认json转换的LocalDateTime的序列化器和反序列化器
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer objectMapperBuilderCustomizer(LocalDateTimeSerializer serializer,LocalDateTimeDeserializer deserializer){
        /**
         * 注册某个指定类型的序列化器和反序列化器
         */
        return builder -> builder.serializerByType(LocalDateTime.class,serializer).deserializerByType(LocalDateTime.class,deserializer);
    }



}
