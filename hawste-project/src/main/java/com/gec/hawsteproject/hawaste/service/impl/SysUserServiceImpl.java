package com.gec.hawsteproject.hawaste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.hawsteproject.hawaste.entity.SysUser;
import com.gec.hawsteproject.hawaste.mapper.SysUserMapper;
import com.gec.hawsteproject.hawaste.service.ISysUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public SysUser selectByNameAndPwd(SysUser sysUser){
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",sysUser.getUsername())
                .eq("password",sysUser.getPassword());
        return baseMapper.selectOne(wrapper);
    }


    @Override
    public List<SysUser>  selectByRid(long rid){
        return baseMapper.selectByRid(rid);
    }

    @Override
    public List<SysUser> selectNoRole(@Param("rid") long rid, @Param("oid") long oid){
        return baseMapper.selectNoRole(rid,oid);
    }

    @Override
    public SysUser selectByName(String name){
        return getOne(new QueryWrapper<SysUser>().eq("username",name));
    }
}
