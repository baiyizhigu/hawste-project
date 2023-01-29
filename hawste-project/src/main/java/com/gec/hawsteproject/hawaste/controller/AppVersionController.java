package com.gec.hawsteproject.hawaste.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.hawsteproject.hawaste.entity.AppVersion;
import com.gec.hawsteproject.hawaste.entity.ResultBean;
import com.gec.hawsteproject.hawaste.entity.SysUser;
import com.gec.hawsteproject.hawaste.service.IAppVersionService;
import com.gec.hawsteproject.hawaste.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
@RestController
@RequestMapping("/manager/app")
public class AppVersionController {

    @Autowired
    IAppVersionService service;

 /*   @RequestMapping("select")
    public ResultBean<Page> select(@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize){
        Page<AppVersion> page = service.page(new Page<AppVersion>(pageNum, pageSize));
        return ResultBean.ok(page);
    }*/

    @RequestMapping("select")
    public ResultBean<Page> select(@RequestParam("current") Integer current,@RequestParam("size") Integer size){
        //默认的page()方法，只能对Page对象的相关属性赋值，子类PageInfo定义的属性并没有赋值过程
        PageInfo<AppVersion> page = service.page(new PageInfo<AppVersion>(current, size));
        page.setNavigatePage();//设置分页对象的导航栏数据
        return ResultBean.ok(page);
    }


    /**
     * 查询某个app
     */
    @RequestMapping("selectOne")
    public ResultBean<AppVersion> selectOne(Long id){
        //模拟异常出现
       /* String str = null;
        str.length();*/
        return ResultBean.ok(service.getById(id));
    }

    /**
     * 添加或者更新的方法，如果有主键则是更新，无则是插入
     */
    @RequestMapping(value = "saveOrUpdate",method = RequestMethod.POST)
    public ResultBean saveOrUpdate(@RequestBody AppVersion appVersion, HttpSession session){
        if(appVersion.getId()==null){
            //获取用户信息
            SysUser loginUser = (SysUser) session.getAttribute("loginUser");
            appVersion.setCreateBy(loginUser.getName());
        }
        //公共类ServiceImpl提供的方法
        //无异常返回的情况，如果有异常，交由统一异常处理Controller执行
        service.saveOrUpdate(appVersion);
        return ResultBean.ok();
    }

    /**
     * 根据id删除元素 ，是逻辑删除
     * @param id
     * @return
     */
    @RequestMapping("delete")
    public ResultBean delete(Long id){
        service.removeById(id);
        return ResultBean.ok();
    }
}
