package com.gec.hawsteproject.hawaste.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawsteproject.hawaste.domain.WorkOrderDetailDo;
import com.gec.hawsteproject.hawaste.domain.WorkOrderDo;
import com.gec.hawsteproject.hawaste.entity.WorkOrder;
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
public interface IWorkOrderService extends IService<WorkOrder> {

    IPage<WorkOrderDo> selectByCondition(IPage<WorkOrderDo> page, Map<String, Object> params);

    WorkOrderDetailDo selectDetailById(Long id);
}
