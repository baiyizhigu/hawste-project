package com.gec.hawsteproject.hawaste.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.hawsteproject.hawaste.domain.ExamineDo;
import com.gec.hawsteproject.hawaste.entity.ResultBean;
import com.gec.hawsteproject.hawaste.service.IExamineService;
import com.gec.hawsteproject.hawaste.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/manager/examine")
public class ExamineController {
    @Autowired
    IExamineService service;
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
    public ResultBean<Page<ExamineDo>> select(@PathVariable Integer current,
                                   @PathVariable Integer size,
                                   @RequestParam Map<String,Object> params){
        PageInfo<ExamineDo> page = (PageInfo<ExamineDo>) service.selectByCondition(new PageInfo<ExamineDo>(current, size), params);
        //设置分页导航栏数据
        page.setNavigatePage();
        return ResultBean.ok(page);
    }
}
