package com.gec.hawsteproject.hawaste.controller;


import com.gec.hawsteproject.hawaste.entity.ResultBean;
import com.gec.hawsteproject.hawaste.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
@RestController
@RequestMapping("/manager/user")
public class SysUserController {

    @Autowired
    ISysUserService userService;

    @RequestMapping("selectByRid/{rid}")
    public ResultBean selectByRid(@PathVariable("rid") long rid){
        return ResultBean.ok(userService.selectByRid(rid));
    }

    @RequestMapping("selectNoRole/{rid}/{oid}")
    public ResultBean selectNoRole(@PathVariable("rid") long rid,@PathVariable("oid")long oid){
        return ResultBean.ok(userService.selectNoRole(rid,oid));
    }
}
