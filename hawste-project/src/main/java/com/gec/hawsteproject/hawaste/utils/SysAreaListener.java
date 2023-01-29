package com.gec.hawsteproject.hawaste.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.gec.hawsteproject.hawaste.entity.SysArea;
import com.gec.hawsteproject.hawaste.service.ISysAreaService;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 读取excel监听器：
 * 1.每次读取一行excel就会通过映射封装成java对象，调用监听器的invoke方法
 * 2.读取完毕后，会调用doAfterAllAnalysed做最后的逻辑处理
 */
@NoArgsConstructor
public class SysAreaListener extends AnalysisEventListener<SysArea> {

    //用于读取到excel数据后的缓存，当达到一定数量后批量插入到数据库中
    private List<SysArea> sysAreas = new ArrayList<>();
    private ISysAreaService service;

    /*
    * 通过构造器传入spring管理的对象
    * */
    public SysAreaListener(ISysAreaService service) {
        this.service = service;
    }

    //每次读取一行excel就会通过映射封装成java对象，调用监听器的invoke方法
    @Override
    public void invoke(SysArea sysArea, AnalysisContext analysisContext) {
        sysAreas.add(sysArea);
        if (sysAreas.size()==10){//每10个对象插入一次
            service.saveBatch(sysAreas);//批量插入到数据库
            sysAreas.clear();//清空集合
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//        System.out.println("读取完成");
        if (sysAreas.size()>0){
            service.saveBatch(sysAreas);//批量插入到数据库
            sysAreas.clear();//清空集合
        }
    }
}
