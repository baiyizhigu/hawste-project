package com.gec.hawsteproject.hawaste.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.hawsteproject.hawaste.domain.SysRoleDo;
import com.gec.hawsteproject.hawaste.entity.SysRole;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
public interface ISysRoleService extends IService<SysRole> {

    IPage<SysRoleDo> selectByCondition(IPage<SysRoleDo> page, Map<String, Object> params);

    SysRoleDo selectOne(long rid);

    List<SysRole> selectByUid(Long id);
}
