package com.gec.hawsteproject.hawaste.config;

/**
 * WebMvcConfigurer定义了spring mvc相关处理方法，如注册拦截器、设置统一静态资源放行等
 */
/*@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    LoginInterceptor loginInterceptor;

    *//**
     * 注册拦截器，设置拦截规则
     * @param registry
     *//*
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                //设置拦截规则  拦截所有/manager开头的请求和首页
                .addPathPatterns("/manager/**","/index.html","/")
                //忽略拦截规则   可省略
                .excludePathPatterns("/login.html","/notlogin.html");
    }
}*/
