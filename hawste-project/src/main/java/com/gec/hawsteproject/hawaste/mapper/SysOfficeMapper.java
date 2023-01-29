package com.gec.hawsteproject.hawaste.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.hawsteproject.hawaste.entity.SysOffice;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 机构表 Mapper 接口
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
public interface SysOfficeMapper extends BaseMapper<SysOffice> {

    /**
     * 根据角色id查询已授权公司
     */
    @Select("SELECT  " +
            "   sof.*   " +
            "FROM  " +
            "   sys_role sr,  " +
            "   sys_role_office sro,  " +
            "   sys_office sof   " +
            "WHERE  " +
            "   sr.del_flag = 0   " +
            "   AND sro.del_flag = 0   " +
            "   AND sof.del_flag = 0   " +
            "   AND sr.id = #{rid}   " +
            "   AND sr.id = sro.role_id   " +
            "   AND sro.office_id = sof.id")
    List<SysOffice> selectByRid(long rid);
}
