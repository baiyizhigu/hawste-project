package com.gec.hawsteproject.hawaste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.hawsteproject.hawaste.domain.QualificationDo;
import com.gec.hawsteproject.hawaste.entity.Qualification;
import com.gec.hawsteproject.hawaste.mapper.QualificationMapper;
import com.gec.hawsteproject.hawaste.service.IQualificationService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
@Service
public class QualificationServiceImpl extends ServiceImpl<QualificationMapper, Qualification> implements IQualificationService {

    @Override
    public IPage<QualificationDo> selectByCondition(IPage<QualificationDo> page, Map<String, Object> params){
        QueryWrapper<QualificationDo> queryWrapper = new QueryWrapper<>();
        /*拼接动态sql
        * WHERE
            qu.del_flag = 0
            AND qu.type = 1
            AND qu.`check` = 0
            AND DATE( qu.create_date ) >= '2019-01-01'
            AND DATE( qu.create_date ) <= '2019-01-11'
        * */
        queryWrapper
                .eq("qu.del_flag","0")
                .eq(checkParams(params,"type"),"qu.type",params.get("type"))
                .eq(checkParams(params,"check"),"qu.`check`",params.get("check"))
                .ge(checkParams(params,"startDate"),"DATE( qu.create_date )",params.get("startDate"))
                .le(checkParams(params,"endDate"),"DATE( qu.create_date )",params.get("endDate"));
        return baseMapper.selectByCondition(page,queryWrapper);
    }

    public  boolean checkParams(Map<String, Object> params, String key){
        return params.containsKey(key)&&!ObjectUtils.isEmpty(params.get(key));
    }


}
