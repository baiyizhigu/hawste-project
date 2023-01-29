package com.gec.hawsteproject.hawaste.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.hawsteproject.hawaste.domain.DetailDo;
import com.gec.hawsteproject.hawaste.entity.Detail;
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
public interface DetailMapper extends BaseMapper<Detail> {

    /**
     * 根据工单id查询详单、危废品分类、危废品信息
     * @param oid
     * @return
     */
    @Select("SELECT " +
            "  de.*, " +
            "  wt.CODE waste_type_code, " +
            "  wt.NAME waste_type_name, " +
            "  wa.CODE waste_code  " +
            "FROM " +
            "  detail de, " +
            "  waste_type wt, " +
            "  waste wa  " +
            "WHERE " +
            "  de.del_flag = 0  " +
            "  AND de.work_order_id = #{oid}  " +
            "  AND de.waste_type_id = wt.id  " +
            "  AND de.waste_id = wa.id")
    List<DetailDo> selectByOrderId(Long oid);
}
