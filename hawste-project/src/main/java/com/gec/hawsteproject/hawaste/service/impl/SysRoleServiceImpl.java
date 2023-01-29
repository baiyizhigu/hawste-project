package com.gec.hawsteproject.hawaste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.hawsteproject.hawaste.domain.SysRoleDo;
import com.gec.hawsteproject.hawaste.entity.*;
import com.gec.hawsteproject.hawaste.mapper.SysOfficeMapper;
import com.gec.hawsteproject.hawaste.mapper.SysResourceMapper;
import com.gec.hawsteproject.hawaste.mapper.SysRoleMapper;
import com.gec.hawsteproject.hawaste.service.ISysRoleOfficeService;
import com.gec.hawsteproject.hawaste.service.ISysRoleResourceService;
import com.gec.hawsteproject.hawaste.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    SysResourceMapper resourceMapper;

    @Autowired
    SysOfficeMapper officeMapper;

    @Autowired
    ISysRoleResourceService roleResourceService;

    @Autowired
    ISysRoleOfficeService roleOfficeService;

    /**
     * 根据动态条件查询列表并分页
     * @param page      分页对象
     * @param params
     * @return
     *
     * SELECT
     * 	sr.*,
     * 	so.NAME office_name
     * FROM
     * 	sys_role sr,
     * 	sys_office so
     * WHERE
     * 	sr.del_flag = 0
     * 	AND sr.office_id = so.id
     * 	AND sr.data_scope = 1
     * 	AND sr.office_id = 56
     * 	AND sr.NAME LIKE CONCAT( '%', '管理', '%' )
     * 		--  and
     * --  sr.remarks like CONCAT('%','管理','%')
     */
    @Override
    public IPage<SysRoleDo> selectByCondition(IPage<SysRoleDo> page, Map<String, Object> params){
        QueryWrapper< SysRoleDo> queryWrapper = new QueryWrapper<>();
        queryWrapper
                //通过apply拼接固定的sql
                .apply(	"sr.del_flag = 0 AND sr.office_id = so.id" )
                .eq(params.containsKey("dataScope")&&!ObjectUtils.isEmpty(params.get("dataScope")),
                        "sr.data_scope",
                        params.get("dataScope"))
                .eq(params.containsKey("id")&&!ObjectUtils.isEmpty(params.get("id")),
                        "sr.office_id",
                        params.get("id"))
                .like(params.containsKey("name")&&!ObjectUtils.isEmpty(params.get("name")),
                        "sr.NAME",
                        params.get("name"))
                //为了避免层级关系的逻辑问题，需要在参数前后加","
                .like(params.containsKey("remarks")&&!ObjectUtils.isEmpty(params.get("remarks")),
                        "sr.remarks",
                        params.get("remarks"));
        return baseMapper.selectByCondition(page,queryWrapper);
    }


    /**
     * 根据角色id查询信息：
     * 根据角色id查询角色信息和公司名
     * 根据角色id查询权限信息
     * 根据角色id查询已授权公司
     * @param rid
     * @return
     */
    @Override
    public SysRoleDo selectOne(long rid){
//        根据角色id查询角色信息和公司名
        SysRoleDo roleDo = baseMapper.selectByRid(rid);
//        根据角色id查询权限信息
        List<SysResource> resources = resourceMapper.selectByRid(rid);
        roleDo.setResources(resources);
        if ("9".equals(roleDo.getDataScope())){
            //        根据角色id查询已授权公司
            List<SysOffice> offices = officeMapper.selectByRid(rid);
            roleDo.setOffices(offices);
        }
        return roleDo;
    }

    /**
     * 根据用户id查询其角色信息
     * @param id
     * @return
     */
    @Override
    public List<SysRole> selectByUid(Long id) {
        return baseMapper.selectByUid(id);
    }


    /**
     * 更新role的方法（saveOrUpdate最终调用更新的方法）
     * 1.更新role
     * 2.更新role的resources
     * a.前端判断是否需要更新，如果不一致则更新
     * b.如果一致，则将resources置空，不更新
     * 3.更新role的offices
     * a.前端判断是否需要更新，如果不一致则更新,offices不为null
     * b.如果offices空，且dataScope是9，则不更新
     * c.如果offices空，dataScope不是9，则取消跨机构授权
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public boolean updateById(SysRole entity) {
        //前端封装的是do对象
        SysRoleDo roleDo = (SysRoleDo) entity;
        //1.更新role
        super.updateById(entity);

        //2.更新role的resources
        //a.前端判断是否需要更新，如果不一致则更新
        List<SysResource> resources = roleDo.getResources();
        if(!ObjectUtils.isEmpty(resources)){
            this.updateRoleResources(roleDo.getId(),resources);
        }//b.如果一致，则将resources置空，不更新

        /*3.更新role的offices
         * a.前端判断是否需要更新，如果不一致则更新,offices不为null
         * b.如果offices空，且dataScope是9，则不更新
         * c.如果offices空，dataScope不是9，则取消跨机构授权
        * */
        List<SysOffice> offices = roleDo.getOffices();
        if(!ObjectUtils.isEmpty(offices)){//a.前端判断是否需要更新，如果不一致则更新,offices不为null
            this.updateRoleOffices(roleDo.getId(),offices);
        }else{
            //b.如果offices空，且dataScope是9，则不更新
            //c.如果offices空，dataScope不是9，则取消跨机构授权
            if(!"9".equals(roleDo.getDataScope())){
                roleOfficeService.remove(new QueryWrapper<SysRoleOffice>().eq("role_id",roleDo.getId()));
            }
        }
        return super.updateById(entity);
    }

    /**
     * 更新role的resources  sys_role_resource
     */
    public void updateRoleResources(long rid,List<SysResource> resources){
        ArrayList<SysRoleResource> list = new ArrayList<>();
        resources.forEach(resource->{
            SysRoleResource roleResource = new SysRoleResource();
            roleResource.setRoleId(rid);
            roleResource.setResourceId(resource.getId());
            list.add(roleResource);
        });
        //删除旧值
        roleResourceService.remove(new QueryWrapper<SysRoleResource>().eq("role_id",rid));
        //批量插入新值
        roleResourceService.saveBatch(list);
    }

    /*
    * 更新角色的跨机构授权   sys_role_office
    * */
    public void updateRoleOffices(long rid,List<SysOffice> offices){
        ArrayList<SysRoleOffice> list = new ArrayList<>();
        offices.forEach(resource->{
            SysRoleOffice sysRoleOffice = new SysRoleOffice();
            sysRoleOffice.setRoleId(rid);
            sysRoleOffice.setOfficeId(resource.getId());
            list.add(sysRoleOffice);
        });
        //删除旧值
        roleOfficeService.remove(new QueryWrapper<SysRoleOffice>().eq("role_id",rid));
        //批量插入新值
        roleOfficeService.saveBatch(list);
    }
}
