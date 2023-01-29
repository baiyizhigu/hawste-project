package com.gec.hawsteproject;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawsteproject.hawaste.entity.Statute;
import com.gec.hawsteproject.hawaste.mapper.StatuteMapper;
import com.gec.hawsteproject.hawaste.service.IStatuteService;
import com.gec.hawsteproject.hawaste.utils.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StatuteTests {


    @Autowired
    StatuteMapper examineMapper;

    @Autowired
    IStatuteService examineService;

    @Test
    void contextLoads() {
        PageInfo<Statute> pageInfo = new PageInfo<>(1, 5);

        IPage<Statute> page = examineService.selectByCondition(pageInfo, 1);
        System.out.println(page.getRecords());
    }

}
