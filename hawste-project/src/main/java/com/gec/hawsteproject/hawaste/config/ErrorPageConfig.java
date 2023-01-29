package com.gec.hawsteproject.hawaste.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 错误页面配置，当发生错误的时候，根据错误类型跳转到指定页面
 * HttpStatus枚举类
 */
@Configuration
public class ErrorPageConfig implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        //设置404错误类型的显示页面
        ErrorPage error404 = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
        registry.addErrorPages(error404);
    }
}
