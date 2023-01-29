package com.gec.hawsteproject.hawaste.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@Slf4j  //lombok  日志处理  自动创建日志对象
public class MybatisPlusConfig {


    /*
    * 添加拦截插件，扩展功能
    * 乐观锁支持
    * */
    @Bean
    public MybatisPlusInterceptor getMybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //注册乐观锁插件
//        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }


    @Bean
    public MetaObjectHandler getMetaObjectHandler(){
       return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                //插入的自动填充逻辑处理
                log.info("开始插入的自动填充......");
                //插入的填充也可以设置数据库默认值来实现
                setFieldValByName("createDate", LocalDateTime.now(),metaObject);
                setFieldValByName("updateDate",LocalDateTime.now(),metaObject);
                setFieldValByName("delFlag","0",metaObject);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                //更新的自动填充逻辑处理
                log.info("开始更新的自动填充......");
                setFieldValByName("updateDate",LocalDateTime.now(),metaObject);
            }
        };
    }
}
