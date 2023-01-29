package com.gec.hawsteproject;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawsteproject.hawaste.domain.QualificationDo;
import com.gec.hawsteproject.hawaste.mapper.QualificationMapper;
import com.gec.hawsteproject.hawaste.service.IQualificationService;
import com.gec.hawsteproject.hawaste.utils.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class QualificationTests {


    @Autowired
    QualificationMapper qualificationMapper;

    @Autowired
    IQualificationService qualificationService;

    @Test
    void contextLoads() {
        PageInfo<QualificationDo> pageInfo = new PageInfo<>(1, 5);
        HashMap<String, Object> map = new HashMap<>();

        map.put("type",1);
        map.put("check",0);
        map.put("startDate","2019-01-01");
        map.put("endDate","2019-01-11");
        IPage<QualificationDo> page = qualificationService.selectByCondition(pageInfo, map);
        System.out.println(page.getRecords());
    }

}
