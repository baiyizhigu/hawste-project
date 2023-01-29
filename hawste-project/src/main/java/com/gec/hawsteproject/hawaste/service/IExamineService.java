package com.gec.hawsteproject.hawaste.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawsteproject.hawaste.domain.ExamineDo;
import com.gec.hawsteproject.hawaste.entity.Examine;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
public interface IExamineService extends IService<Examine> {

    IPage<ExamineDo> selectByCondition(IPage<ExamineDo> page, Map<String, Object> params);
}
