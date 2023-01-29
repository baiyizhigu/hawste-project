package com.gec.hawsteproject.hawaste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.hawsteproject.hawaste.domain.DetailDo;
import com.gec.hawsteproject.hawaste.domain.TransferDo;
import com.gec.hawsteproject.hawaste.domain.WorkOrderDetailDo;
import com.gec.hawsteproject.hawaste.domain.WorkOrderDo;
import com.gec.hawsteproject.hawaste.entity.WorkOrder;
import com.gec.hawsteproject.hawaste.mapper.DetailMapper;
import com.gec.hawsteproject.hawaste.mapper.TransferMapper;
import com.gec.hawsteproject.hawaste.mapper.WorkOrderMapper;
import com.gec.hawsteproject.hawaste.service.IWorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
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
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrder> implements IWorkOrderService {

    @Autowired
    DetailMapper detailMapper;

    @Autowired
    TransferMapper transferMapper;

    /**
     * WHERE
     * 	wo.del_flag = 0
     * 	AND wo.`status` = 1
     * 	AND DATE( wo.create_date ) >= '2016-09-01'
     * 	AND DATE( wo.create_date ) <= '2016-11-30'
     * 	AND ( co.id = 56 OR `to`.id = 56 OR ro.id = 56 )
     * @param page
     * @param params
     * @return
     */
    @Override
    public IPage<WorkOrderDo> selectByCondition(IPage<WorkOrderDo> page, Map<String, Object> params){
        QueryWrapper<WorkOrderDo> wrapper = new QueryWrapper<>();
        wrapper.eq("wo.del_flag ","0")
                .eq(params.containsKey("status")&& !ObjectUtils.isEmpty(params.get("status")),
                        "wo.`status`",params.get("status"))
                .ge(params.containsKey("startDate")&& !ObjectUtils.isEmpty(params.get("startDate")),
                        "wo.create_date",params.get("startDate"))
                .le(params.containsKey("endDate")&& !ObjectUtils.isEmpty(params.get("endDate")),
                        "wo.create_date",params.get("endDate"))
                //嵌套语句处理
                .and(params.containsKey("officeId")&& !ObjectUtils.isEmpty(params.get("officeId")),
                        qw -> qw.eq("co.id",params.get("officeId"))
                                .or()
                                .eq("`to`.id",params.get("officeId"))
                                .or()
                                .eq("ro.id",params.get("officeId")));
        return baseMapper.selectByCondition(page,wrapper);
    }

    /**
     * 根据oid查询以下：
     * 1.workorder信息和用户、用户公司信息
     * 2.查询detail
     * 3.查询transfer记录
     * @param id
     * @return
     */
    @Override
    public WorkOrderDetailDo selectDetailById(Long id){
        /*1.workorder信息和用户、用户公司信息*/
        WorkOrderDetailDo orderDetailDo = baseMapper.selectByOrderId(id);
        /*2.查询detail*/
        List<DetailDo> detailDos = detailMapper.selectByOrderId(id);
        /*3.查询transfer记录*/
        List<TransferDo> transferDos = transferMapper.selectByOrderId(id);
        orderDetailDo.setDetails(detailDos);
        orderDetailDo.setTransfers(transferDos);

        return orderDetailDo;
    }
}
