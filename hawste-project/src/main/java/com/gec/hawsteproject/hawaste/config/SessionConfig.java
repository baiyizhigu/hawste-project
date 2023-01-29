package com.gec.hawsteproject.hawaste.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
//开启springsession ,并使用redis存储session
@EnableRedisHttpSession
public class SessionConfig {
}
