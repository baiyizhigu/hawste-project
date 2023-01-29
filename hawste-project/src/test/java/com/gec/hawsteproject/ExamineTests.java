package com.gec.hawsteproject;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawsteproject.hawaste.domain.ExamineDo;
import com.gec.hawsteproject.hawaste.mapper.ExamineMapper;
import com.gec.hawsteproject.hawaste.service.IExamineService;
import com.gec.hawsteproject.hawaste.utils.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class ExamineTests {


    @Autowired
    ExamineMapper examineMapper;

    @Autowired
    IExamineService examineService;

    @Test
    void contextLoads() {
        PageInfo<ExamineDo> pageInfo = new PageInfo<>(1, 5);
        HashMap<String, Object> map = new HashMap<>();
        /**
         * AND ex.type = 1
         * 	AND so.id = 56
         * 	AND su.NAME LIKE CONCAT( '%', '工作', '%' )
         */
        map.put("type",1);
//        map.put("officeId",56);
        map.put("name","工作");
        IPage<ExamineDo> page = examineService.selectByCondition(pageInfo, map);
        System.out.println(page.getRecords());
    }

}
