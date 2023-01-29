package com.gec.hawsteproject;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.hawsteproject.hawaste.entity.AppVersion;
import com.gec.hawsteproject.hawaste.mapper.AppVersionMapper;
import com.gec.hawsteproject.hawaste.service.IAppVersionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HawsteProjectApplicationTests {


    @Autowired
    AppVersionMapper appVersionMapper;

    @Autowired
    IAppVersionService appVersionService;

    @Test
    void contextLoads() {
        AppVersion appVersion = appVersionMapper.selectById(1);
        System.out.println(appVersion);
    }

    @Test
    void contextLoads2() {
        Page<AppVersion> page = new Page<>(1, 5);
        appVersionService.page(page);
        page.getRecords().forEach(System.out::println);
    }
}
