package com.gec.hawsteproject.hawaste.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.hawsteproject.hawaste.entity.SysResource;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
public interface ISysResourceService extends IService<SysResource> {

    List<SysResource> selectByRid(Long id);

    List<SysResource> selectByRids(List<Long> rids);
}
