package com.gec.hawsteproject;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawsteproject.hawaste.domain.SysRoleDo;
import com.gec.hawsteproject.hawaste.mapper.SysRoleMapper;
import com.gec.hawsteproject.hawaste.service.ISysRoleService;
import com.gec.hawsteproject.hawaste.utils.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class SysRoleTests {


    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    ISysRoleService sysRoleService;

    @Test
    void contextLoads() {
        PageInfo<SysRoleDo> pageInfo = new PageInfo<>(1, 5);
        HashMap<String, Object> map = new HashMap<>();

        map.put("dataScope",1);
        map.put("id",56);
        map.put("name","管理");

        IPage<SysRoleDo> page = sysRoleService.selectByCondition(pageInfo, map);
        System.out.println(page.getRecords());
    }

    @Test
    void selectOne(){
        SysRoleDo sysRoleDo = sysRoleService.selectOne(27);
        System.out.println(sysRoleDo);
    }

}
