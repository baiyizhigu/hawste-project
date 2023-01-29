package com.gec.hawsteproject.hawaste.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawsteproject.hawaste.entity.Statute;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
public interface IStatuteService extends IService<Statute> {

    IPage<Statute> selectByCondition(IPage<Statute> page, Integer type);
}
