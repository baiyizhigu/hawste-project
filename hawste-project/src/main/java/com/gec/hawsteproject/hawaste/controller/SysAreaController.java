package com.gec.hawsteproject.hawaste.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.hawsteproject.hawaste.domain.SysAreaDo;
import com.gec.hawsteproject.hawaste.entity.ResultBean;
import com.gec.hawsteproject.hawaste.entity.SysArea;
import com.gec.hawsteproject.hawaste.service.ISysAreaService;
import com.gec.hawsteproject.hawaste.utils.PageInfo;
import lombok.SneakyThrows;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 区域表 前端控制器
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
@RestController
@RequestMapping("/manager/area")
//必须是两个角色之一才能访问
@RequiresRoles(value = {"超级管理员","危废平台服务人员"},logical = Logical.OR)
public class SysAreaController {

    @Autowired
    ISysAreaService service;
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
    @RequiresPermissions("area:select")
    public ResultBean<Page<SysAreaDo>> select(@PathVariable Integer current,
                                              @PathVariable Integer size,
                                              @RequestParam Map<String,Object> params){
        PageInfo<SysAreaDo> page = (PageInfo<SysAreaDo>) service.selectByCondition(new PageInfo<SysAreaDo>(current, size), params);
        //设置分页导航栏数据
        page.setNavigatePage();
        return ResultBean.ok(page);
    }

    @RequestMapping("list")
    public ResultBean<List<SysArea>> list(){
        return ResultBean.ok(service.list());
    }


    /**
     * 查询某个SysAreaDo
     */
    @RequestMapping("selectOne")
    public ResultBean<SysAreaDo> selectOne(Long id){
        return ResultBean.ok(service.selectAreaById(id));
    }

    /**
     * 添加或者更新的方法，如果有主键则是更新，无则是插入
     */
    @RequestMapping(value = "saveOrUpdate",method = RequestMethod.POST)
    public ResultBean saveOrUpdate(@RequestBody SysAreaDo sysArea){
        //公共类ServiceImpl提供的方法
        //无异常返回的情况，如果有异常，交由统一异常处理Controller执行
//        service.saveOrUpdate(sysArea);
        service.updateAreaAndSub(sysArea);
        return ResultBean.ok();
    }

    /**
     * 根据id删除元素 ，是逻辑删除
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @RequiresPermissions("area:delete")
    public ResultBean delete(Long id){
        //TODO 需要修改为删除当前区域后，删除所有子区域
//        service.removeById(id);
        service.removeAreaAndSub(id);
        return ResultBean.ok();
    }

    /**
     * 导出excel：
     * 1.设置响应头，告诉浏览器是文件下载
     * 2.处理文件名避免乱码
     * 3.将数据读取到响应流
     * 4.响应流写出(spring 自动完成)
     * @param response
     */
    @RequestMapping("exportExcel")
    @SneakyThrows
    @RequiresPermissions("area:exportExcel")
    public void exportExcel(HttpServletResponse response)  {
        /**
         * 设置内容描述，告诉浏览器有附件
         */
        response.addHeader("Content-Disposition",
                "attachment;filename="
                        +new String("区域列表数据.xlsx".getBytes(),"ISO-8859-1"));
        /**
         * 将响应流对象，传入方法exportExcel中，在方法中通过easyexcel的api获取所有区域对象，转换成excel输出流
         */
        service.exportExcel(response.getOutputStream());
    }

    /**
     * 区域导入，将上传的文件放入到封装的文件处理对象file中，通过EasyExcel实现转换java对象，插入数据库
     * @param file  与前端上传文件对象名对应
     * @return
     */
    @SneakyThrows
    @RequestMapping("importExcel")
    @RequiresPermissions("area:importExcel")
    public ResultBean importExcel(MultipartFile file){
        service.importExcel(file.getInputStream());
        return ResultBean.ok();
    }
}
