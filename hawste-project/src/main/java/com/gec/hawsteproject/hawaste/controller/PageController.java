package com.gec.hawsteproject.hawaste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 页面访问Controller
 */
@Controller
public class PageController {

    /**
     * 一级路径页面跳转  如  index.html
     *
     * /index.html  ->  url="index"
     */
    @RequestMapping("{url}.html")
    public String module(@PathVariable("url") String url){
        return url;
    }

    /**
     * 三级路径页面跳转  如  /manager/app/app.html
     *
     * /manager/app/app.html  ->
     * @param module     对于管理系统模块 manager
     * @param classify   对应分类-如app、area等
     * @param url        对应分类下的页面，如列表页
     * @return
     */
    @RequestMapping("{module}/{classify}/{url}.html")
    public String module3(@PathVariable("module") String module,
                          @PathVariable("classify") String classify,
                          @PathVariable("url") String url){
        return module+"/"+classify+"/"+url;
    }

    /**
     * 四级路径页面跳转  如  /manager/work/admin/work.html
     *
     * /manager/app/app.html  ->
     * @param module     对于管理系统模块 manager
     * @param classify   对应分类-如app、area等
     * @param role       对应角色-如admin,company
     * @param url        对应分类下的页面，如列表页
     * @return
     */
    @RequestMapping("{module}/{classify}/{role}/{url}.html")
    public String module4(@PathVariable("module") String module,
                          @PathVariable("classify") String classify,
                          @PathVariable("role") String role,
                          @PathVariable("url") String url){
        return module+"/"+classify+"/"+role+"/"+url;
    }
}
