package com.gec.hawsteproject.hawaste.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawsteproject.hawaste.domain.QualificationDo;
import com.gec.hawsteproject.hawaste.entity.Qualification;
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
public interface IQualificationService extends IService<Qualification> {

    IPage<QualificationDo> selectByCondition(IPage<QualificationDo> page, Map<String, Object> params);
}
