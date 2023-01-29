package com.gec.hawsteproject.hawaste.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.hawsteproject.hawaste.entity.ResultBean;
import com.gec.hawsteproject.hawaste.entity.Statute;
import com.gec.hawsteproject.hawaste.service.IStatuteService;
import com.gec.hawsteproject.hawaste.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
@RestController
@RequestMapping("/manager/statute")
public class StatuteController {
    @Autowired
    IStatuteService service;
    /**
     * 动态条件分页查询
     * 通过restful请求方式 在地址栏传参分页信息
     * 如果占位符与方法参数名一致，可以省略
     * @param current
     * @param size
     * @param type  动态条件封装对象
     * 参数通过@RequestParam绑定，会校验type是否有值，如果没有值则会报错Not present
     *              required = false表示不是必须的
     * @return
     */
    @RequestMapping("select/{current}/{size}")
    public ResultBean<Page<Statute>> select(@PathVariable Integer current,
                                            @PathVariable Integer size,
                                            @RequestParam(required = false) Integer type){
        PageInfo<Statute> page = (PageInfo<Statute>) service.selectByCondition(new PageInfo<Statute>(current, size), type);
        //设置分页导航栏数据
        page.setNavigatePage();
        return ResultBean.ok(page);
    }

    /*
    * 根据id查询
    * */
    @RequestMapping("selectOne")
    public ResultBean selectOne(Long id){
        
        return ResultBean.ok(service.getById(id));
    }


    /**
     * 添加或者更新的方法，如果有主键则是更新，无则是插入
     */
    @RequestMapping(value = "saveOrUpdate",method = RequestMethod.POST)
    public ResultBean saveOrUpdate(@RequestBody Statute statute){
        //公共类ServiceImpl提供的方法
        //无异常返回的情况，如果有异常，交由统一异常处理Controller执行
        service.saveOrUpdate(statute);
        return ResultBean.ok();
    }
}
