package com.gec.hawsteproject.hawaste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.hawsteproject.hawaste.entity.Statute;
import com.gec.hawsteproject.hawaste.mapper.StatuteMapper;
import com.gec.hawsteproject.hawaste.service.IStatuteService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
@Service
public class StatuteServiceImpl extends ServiceImpl<StatuteMapper, Statute> implements IStatuteService {

    /**
     * 根据动态条件分页查询，返回分页对象
     * @param page      分页对象
     * @param type    动态查询条件
     * @return          分页动态条件查询结果
     */
    @Override
    public IPage<Statute> selectByCondition(IPage<Statute> page, Integer type){
        //1.查询包装对象
        QueryWrapper<Statute> queryWrapper = new QueryWrapper<>();
        //2.拼接where条件
        //只查询部分字段，不查询description
        queryWrapper.select("statute.id," +
                "statute.type," +
                "statute.title," +
                "statute.pub_date," +
                "statute.stem_from ")
                //动态条件type拼接
                .eq(!ObjectUtils.isEmpty(type),"statute.type",type);
        return  baseMapper.selectPage(page,queryWrapper);
        
    }
}
