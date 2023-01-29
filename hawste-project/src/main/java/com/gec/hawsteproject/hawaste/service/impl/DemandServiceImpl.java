package com.gec.hawsteproject.hawaste.service.impl;

import com.gec.hawsteproject.hawaste.entity.Demand;
import com.gec.hawsteproject.hawaste.mapper.DemandMapper;
import com.gec.hawsteproject.hawaste.service.IDemandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户的需求填写 服务实现类
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
@Service
public class DemandServiceImpl extends ServiceImpl<DemandMapper, Demand> implements IDemandService {

}
