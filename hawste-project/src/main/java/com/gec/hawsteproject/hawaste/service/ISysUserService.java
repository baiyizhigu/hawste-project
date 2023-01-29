package com.gec.hawsteproject.hawaste.service;

import com.gec.hawsteproject.hawaste.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
public interface ISysUserService extends IService<SysUser> {

    SysUser selectByNameAndPwd(SysUser sysUser);

    List<SysUser> selectByRid(long rid);

    List<SysUser> selectNoRole(@Param("rid") long rid, @Param("oid") long oid);

    SysUser selectByName(String name);
}
