package com.gec.hawsteproject.hawaste.controller;


import com.gec.hawsteproject.hawaste.entity.ResultBean;
import com.gec.hawsteproject.hawaste.entity.SysOffice;
import com.gec.hawsteproject.hawaste.service.ISysOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 机构表 前端控制器
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
@RestController
@RequestMapping("/manager/company")
public class SysOfficeController {

    @Autowired
    ISysOfficeService service;

    @RequestMapping("list")
    public ResultBean<List<SysOffice>> select(){
        return ResultBean.ok(service.list());
    }
}
