package com.gec.hawsteproject.hawaste.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.hawsteproject.hawaste.domain.SysRoleDo;
import com.gec.hawsteproject.hawaste.entity.ResultBean;
import com.gec.hawsteproject.hawaste.entity.SysUser;
import com.gec.hawsteproject.hawaste.service.ISysRoleService;
import com.gec.hawsteproject.hawaste.service.ISysUserRoleService;
import com.gec.hawsteproject.hawaste.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
@RestController
@RequestMapping("/manager/role")
public class SysRoleController {
    @Autowired
    ISysRoleService service;

    @Autowired
    ISysUserRoleService userRoleService;


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
    public ResultBean<Page<SysRoleDo>> select(@PathVariable Integer current,
                                              @PathVariable Integer size,
                                              @RequestParam Map<String,Object> params){
        PageInfo<SysRoleDo> page = (PageInfo<SysRoleDo>) service.selectByCondition(new PageInfo<SysRoleDo>(current, size), params);
        //设置分页导航栏数据
        page.setNavigatePage();
        return ResultBean.ok(page);
    }

    /**
     * 批量删除角色中的已选人员
     * Long[] ids：设置为long类型数组，可以通过自动封装的方式实现
     * 如果参数为List ids 类型，会自动封装成List<Integer>类型 需要在服务层转换
     */
    @RequestMapping("deleteBatch")
    public ResultBean deleteBatch(long rid ,Long[] ids){
        return ResultBean.ok(userRoleService.deleteBatch(rid, ids));
    }

    /**
     * 角色批量添加用户
     */
    @RequestMapping("insertBatch")
    public ResultBean insertBatch(long rid , Long[] ids){
        return ResultBean.ok(userRoleService.insertBatch(rid, Arrays.asList(ids)));
    }

    /**
     * 查询某个Role
     */
    @RequestMapping("selectOne")
    public ResultBean<SysRoleDo> selectOne(Long id){
        return ResultBean.ok(service.selectOne(id));
    }

    /**
     * 添加或者更新的方法，如果有主键则是更新，无则是插入
     */
    @RequestMapping(value = "saveOrUpdate",method = RequestMethod.POST)
    public ResultBean saveOrUpdate(@RequestBody SysRoleDo sysRoleDo, HttpSession session){
        if(sysRoleDo.getId()==null){
            //获取用户信息
            SysUser loginUser = (SysUser) session.getAttribute("loginUser");
            sysRoleDo.setCreateBy(loginUser.getName());
        }
        //公共类ServiceImpl提供的方法
        //无异常返回的情况，如果有异常，交由统一异常处理Controller执行
        service.saveOrUpdate(sysRoleDo);
        return ResultBean.ok();
    }
}
