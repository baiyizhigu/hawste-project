package com.gec.hawsteproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gec.hawsteproject.hawaste.mapper")
public class HawsteProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(HawsteProjectApplication.class, args);
    }

}
