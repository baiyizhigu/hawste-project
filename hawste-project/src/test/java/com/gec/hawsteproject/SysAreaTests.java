package com.gec.hawsteproject;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawsteproject.hawaste.domain.SysAreaDo;
import com.gec.hawsteproject.hawaste.entity.SysArea;
import com.gec.hawsteproject.hawaste.mapper.SysAreaMapper;
import com.gec.hawsteproject.hawaste.service.ISysAreaService;
import com.gec.hawsteproject.hawaste.utils.LocalDateTimeConverter;
import com.gec.hawsteproject.hawaste.utils.PageInfo;
import com.gec.hawsteproject.hawaste.utils.SysAreaListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class SysAreaTests {


    @Autowired
     SysAreaMapper sysAreaMapper;

    @Autowired
    ISysAreaService sysAreaService;

    @Autowired
    LocalDateTimeConverter localDateTimeConverter;

    @Test
    void contextLoads() {
        PageInfo< SysAreaDo> pageInfo = new PageInfo<>(1, 5);
        HashMap<String, Object> map = new HashMap<>();

        map.put("name","山");
        /*map.put("id","1931");*/

        IPage< SysAreaDo> page = sysAreaService.selectByCondition(pageInfo, map);
        System.out.println(page.getRecords());

    }

    @Test
    void testDelete(){
        sysAreaService.removeAreaAndSub(1931L);
    }


    /*
    * easyexcel 写操作
    *  1.构建Excel读/写对象
        2.操作excel对象
        3.导入导出api操作
        4.关闭资源
        * Can not find 'Converter' support class LocalDateTime
        * 默认日期转换器不支持LocalDateTime，需要设置：
        * 1.在@ExcelProperty(converter=xxx)中设置
        * 2.调用registerConverter(xxx)注册转换器
    * */
    @Test
    public void testWrite(){
        EasyExcel
                .write("D:\\java\\hawste-project\\area.xlsx", SysArea.class)
                .sheet()
                .registerConverter(localDateTimeConverter)
                .doWrite(sysAreaMapper.selectList(null));
    }

    /**
     * 读操作
     * 每次读取一行调用监听器，封装成java对象
     * 官方建议不要用spring管理监听器对象，spring中的对象通过构造器传入
     */
    @Test
    public void testRead(){
        EasyExcel
                .read("D:\\java\\hawste-project\\area.xlsx",SysArea.class,new SysAreaListener(sysAreaService))
                .sheet()
                .registerConverter(localDateTimeConverter)
                .doRead();
    }

}
