package com.gec.hawsteproject.hawaste.service.impl;

import com.gec.hawsteproject.hawaste.entity.SysOffice;
import com.gec.hawsteproject.hawaste.mapper.SysOfficeMapper;
import com.gec.hawsteproject.hawaste.service.ISysOfficeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.hawsteproject.hawaste.service.RedisService;
import com.gec.hawsteproject.hawaste.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * <p>
 * 机构表 服务实现类
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
@Service
@Slf4j
public class SysOfficeServiceImpl extends ServiceImpl<SysOfficeMapper, SysOffice> implements ISysOfficeService {


    @Autowired
    RedisService redisService;

    /**
     * 重写list方法，提供从缓存中获取数据的功能
     * 先从缓存中查看是否存在，如果存在则直接返回信息
     * 如果不存在则是第一次查询，需要从数据库中查询数据，并且放入到缓存
     *
     * @return
     */
    @Override
    public List<SysOffice> list() {
        String key = RedisUtil.keyBuilder("SysOffice", "list", (String) null);
        List<SysOffice> list = null;
        Object o = redisService.get(key);
        if(ObjectUtils.isEmpty(o)){
            //第一次查询
            list = super.list();
            redisService.set(key,list,60*30);//放入redis
            log.info("数据缓存到redis:"+list.size()+"条");
            return list;
        }
        //缓存中存在
        list = (List<SysOffice>) o;
        log.info("缓存redis中已存在记录:"+list.size()+"条");
        return list;
    }
}
