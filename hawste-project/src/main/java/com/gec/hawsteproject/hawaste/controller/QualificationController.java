package com.gec.hawsteproject.hawaste.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.hawsteproject.hawaste.domain.QualificationDo;
import com.gec.hawsteproject.hawaste.entity.Qualification;
import com.gec.hawsteproject.hawaste.entity.ResultBean;
import com.gec.hawsteproject.hawaste.service.IQualificationService;
import com.gec.hawsteproject.hawaste.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
@RestController
@RequestMapping("/manager/qualification")
public class QualificationController {
    @Autowired
    IQualificationService service;
    /**
     * 动态条件分页查询
     * 通过restful请求方式 在地址栏传参分页信息
     * 如果占位符与方法参数名一致，可以省略
     * @param current
     * @param size
     * @param params  动态条件封装对象  前端通过get请求封装  key与前端对象属性名一致
     * @return
     */
    @RequestMapping("select/{current}/{size}")
    public ResultBean<Page<QualificationDo>> select(@PathVariable Integer current,
                                              @PathVariable Integer size,
                                              @RequestParam Map<String,Object> params){
        PageInfo<QualificationDo> page = (PageInfo<QualificationDo>) service.selectByCondition(new PageInfo<QualificationDo>(current, size), params);
        //设置分页导航栏数据
        page.setNavigatePage();
        return ResultBean.ok(page);
    }

    /**
     * 查询某个Qualification
     */
    @RequestMapping("selectOne")
    public ResultBean<Qualification> selectOne(Long id){
        return ResultBean.ok(service.getById(id));
    }

    /**
     * 添加或者更新的方法，如果有主键则是更新，无则是插入
     */
    @RequestMapping(value = "saveOrUpdate",method = RequestMethod.POST)
    public ResultBean saveOrUpdate(@RequestBody Qualification qualification){
        //暂时未有登录功能，写死为超级管理员
        //TODO 获取登录用户id
        qualification.setCheckUserId(2L);
        //公共类ServiceImpl提供的方法
        //无异常返回的情况，如果有异常，交由统一异常处理Controller执行
        service.saveOrUpdate(qualification);
        return ResultBean.ok();
    }
}
