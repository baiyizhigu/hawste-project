package com.gec.hawsteproject;

import com.gec.hawsteproject.hawaste.entity.SysOffice;
import com.gec.hawsteproject.hawaste.mapper.SysOfficeMapper;
import com.gec.hawsteproject.hawaste.service.ISysOfficeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SysOfficeTests {


    @Autowired
    SysOfficeMapper sysOfficeMapper;

    @Autowired
    ISysOfficeService sysOfficeService;

    @Test
    void contextLoads() {
        for (SysOffice sysOffice : sysOfficeService.list()) {
            System.out.println(sysOffice);
        }

    }

    
}
