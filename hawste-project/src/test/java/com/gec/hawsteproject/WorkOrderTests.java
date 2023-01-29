package com.gec.hawsteproject;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawsteproject.hawaste.domain.WorkOrderDetailDo;
import com.gec.hawsteproject.hawaste.domain.WorkOrderDo;
import com.gec.hawsteproject.hawaste.mapper.WorkOrderMapper;
import com.gec.hawsteproject.hawaste.service.IWorkOrderService;
import com.gec.hawsteproject.hawaste.utils.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class WorkOrderTests {


    @Autowired
    WorkOrderMapper workOrderMapper;

    @Autowired
    IWorkOrderService workOrderService;

    @Test
    void contextLoads() {
        PageInfo<WorkOrderDo> pageInfo = new PageInfo<>(1, 5);
        HashMap<String, Object> map = new HashMap<>();

        map.put("status",1);
        map.put("officeId",56);
        map.put("startDate","2016-09-01");
        map.put("endDate","2016-11-30");
        IPage<WorkOrderDo> page = workOrderService.selectByCondition(pageInfo, map);
        System.out.println(page.getRecords());
    }

    @Test
    void testSelectDetail(){
        WorkOrderDetailDo workOrderDetailDo = workOrderService.selectDetailById(11L);
        System.out.println(workOrderDetailDo);
    }
}
