package com.gec.hawsteproject;

import com.gec.hawsteproject.hawaste.mapper.SysUserRoleMapper;
import com.gec.hawsteproject.hawaste.service.ISysUserRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SysUserRoleTests {


    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    ISysUserRoleService sysUserRoleService;

    @Test
    void contextLoads() {
        int i = sysUserRoleService.deleteBatch(2L, new Long[]{45L, 47L});
    }

    @Test
    void contextLoads2(){
        List<Long> list = Arrays.asList(45L, 47L);
        sysUserRoleService.insertBatch(25, list);
    }

}
