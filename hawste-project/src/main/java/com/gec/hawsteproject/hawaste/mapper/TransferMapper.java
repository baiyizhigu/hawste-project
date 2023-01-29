package com.gec.hawsteproject.hawaste.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.hawsteproject.hawaste.domain.TransferDo;
import com.gec.hawsteproject.hawaste.entity.Transfer;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
public interface TransferMapper extends BaseMapper<Transfer> {
    /**
     * 根据工单id查询转运记录信息，按照创建时间倒序排列
     * @param oid
     * @return
     */
    @Select("SELECT  " +
            "  tr.*,  " +
            "  su.NAME user_name,  " +
            "  su.phone user_phone   " +
            "FROM  " +
            "  transfer tr,  " +
            "  sys_user su   " +
            "WHERE  " +
            "  tr.del_flag = 0   " +
            "  AND tr.work_order_id = #{oid}   " +
            "  AND tr.oprate_user_id = su.id   " +
            "ORDER BY  " +
            "  tr.create_date DESC")
    List<TransferDo> selectByOrderId(Long oid);
}
