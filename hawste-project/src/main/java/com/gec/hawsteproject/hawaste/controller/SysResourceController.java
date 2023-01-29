package com.gec.hawsteproject.hawaste.controller;


import com.gec.hawsteproject.hawaste.entity.ResultBean;
import com.gec.hawsteproject.hawaste.entity.SysResource;
import com.gec.hawsteproject.hawaste.service.ISysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
@RestController
@RequestMapping("/manager/menu")
public class SysResourceController {

    @Autowired
    ISysResourceService service;

    @RequestMapping("list")
    public ResultBean<List<SysResource>> select(){
        return ResultBean.ok(service.list());
    }
}
