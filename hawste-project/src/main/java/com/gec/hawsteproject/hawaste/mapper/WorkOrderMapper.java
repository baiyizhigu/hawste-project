package com.gec.hawsteproject.hawaste.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.gec.hawsteproject.hawaste.domain.WorkOrderDetailDo;
import com.gec.hawsteproject.hawaste.domain.WorkOrderDo;
import com.gec.hawsteproject.hawaste.entity.WorkOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
public interface WorkOrderMapper extends BaseMapper<WorkOrder> {


    /**
     * 通过mybatis_plus提供的querywrapper实现动态拼接sql
     * @param page
     * @param ew  mybatis_plus调用的时候参数名要么叫ew，要么加上@Param(Constants.WRAPPER)
     *            动态sql拼接${ew.customSqlSegment}
     *            会自动生成where语句
     * @return
     */
    @Select("SELECT " +
            "  wo.*, " +
            "  cu.NAME create_user_name, " +
            "  co.NAME create_office_name, " +
            "  tu.NAME transport_user_name, " +
            "  ru.NAME recipient_user_name  " +
            "FROM " +
            "  work_order wo " +
            "  LEFT JOIN sys_user cu ON wo.create_user_id = cu.id " +
            "  LEFT JOIN sys_office co ON cu.office_id = co.id  " +
            "  LEFT JOIN sys_user tu ON wo.transport_user_id = tu.id " +
            "  LEFT JOIN sys_office `to` ON tu.office_id = `to`.id  " +
            "  LEFT JOIN sys_user ru ON wo.recipient_user_id = ru.id " +
            "  LEFT JOIN sys_office ro ON ru.office_id = ro.id " +
            " ${ew.customSqlSegment}")
    IPage<WorkOrderDo> selectByCondition(IPage<WorkOrderDo> page, @Param(Constants.WRAPPER) Wrapper ew);


    @Select("SELECT  " +
            "  wo.`code`,  " +
            "  wo.id,  " +
            "  wo.`status`,  " +
            "  cu.NAME create_user_name,  " +
            "  cu.phone create_user_phone,  " +
            "  co.NAME create_office_name,  " +
            "  tu.NAME transport_user_name,  " +
            "  tu.phone transport_user_phone,  " +
            "  `to`.NAME transport_office_name,  " +
            "  ru.NAME recipient_user_name,  " +
            "  ru.phone recipient_user_phone,  " +
            "  ro.name recipient_office_name  " +
            "FROM  " +
            "  work_order wo  " +
            "  LEFT JOIN sys_user cu ON wo.create_user_id = cu.id  " +
            "  LEFT JOIN sys_office co ON cu.office_id = co.id   " +
            "  LEFT JOIN sys_user tu ON wo.transport_user_id = tu.id  " +
            "  LEFT JOIN sys_office `to` ON tu.office_id = `to`.id   " +
            "  LEFT JOIN sys_user ru ON wo.recipient_user_id = ru.id  " +
            "  LEFT JOIN sys_office ro ON ru.office_id = ro.id   " +
            "WHERE  " +
            "  wo.del_flag = 0   " +
            "and  " +
            "  wo.id = #{oid}")
    WorkOrderDetailDo selectByOrderId(Long oid);
}
