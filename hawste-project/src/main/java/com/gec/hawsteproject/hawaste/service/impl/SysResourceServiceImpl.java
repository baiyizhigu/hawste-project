package com.gec.hawsteproject.hawaste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gec.hawsteproject.hawaste.entity.SysResource;
import com.gec.hawsteproject.hawaste.mapper.SysResourceMapper;
import com.gec.hawsteproject.hawaste.service.ISysResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements ISysResourceService {

    @Override
    public List<SysResource> selectByRid(Long id) {
        return baseMapper.selectByRid(id);
    }

    /*
    * 根据多个角色id，查询所有的权限
    *
    * SELECT
	sre.*
FROM
	sys_resource sre,
	sys_role_resource srr,
	sys_role sro
WHERE
	sre.del_flag = 0
	AND srr.del_flag = 0
	AND sro.del_flag = 0
	AND sre.id = srr.resource_id
	AND srr.role_id = sro.id
	AND sro.id IN ( 2, 24, 25 )
    * */
    @Override
    public List<SysResource> selectByRids(List<Long> rids) {
        QueryWrapper<SysResource> wrapper = new QueryWrapper<>();
        wrapper.eq("sre.del_flag",0)
                .eq("srr.del_flag",0)
                .eq("sro.del_flag",0)
                .apply("sre.id = srr.resource_id")
                .apply("srr.role_id = sro.id")
                .in("sro.id",rids);
        return baseMapper.selectByRids(wrapper);
    }
}
