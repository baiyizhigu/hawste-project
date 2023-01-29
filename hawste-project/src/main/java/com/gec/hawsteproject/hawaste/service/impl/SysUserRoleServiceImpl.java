package com.gec.hawsteproject.hawaste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.hawsteproject.hawaste.entity.SysUserRole;
import com.gec.hawsteproject.hawaste.mapper.SysUserRoleMapper;
import com.gec.hawsteproject.hawaste.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    /**
     * 根据角色id和人员id数组，删除已选人员信息
     * @param rid
     * @param ids
     * @return
     */
    @Override
    public int deleteBatch(long rid, Long[] ids){
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",rid).in("user_id",ids);
        return baseMapper.delete(wrapper);
    }

    /**
     * 根据角色id和人员id数组，添加待选人员
     * @param rid
     * @param ids
     * @return
     */
    @Override
    public boolean insertBatch(long rid, List<Long> ids){
        ArrayList<SysUserRole> list = new ArrayList<>();
        ids.forEach(id -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(rid);
            userRole.setUserId(id);
            list.add(userRole);
        });
        return this.saveBatch(list);
    }
}
