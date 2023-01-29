package com.gec.hawsteproject.hawaste.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.gec.hawsteproject.hawaste.entity.SysResource;
import org.apache.ibatis.annotations.Param;
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
public interface SysResourceMapper extends BaseMapper<SysResource> {
    /**
     * 根据角色id查询权限信息
     */
    @Select("SELECT " +
            "  sre.id, " +
            "  sre.`name`, " +
            "  sre.common, " +
            "  sre.icon, " +
            "  sre.sort, " +
            "  sre.parent_id, " +
            "  sre.type, " +
            "  sre.url, " +
            "  sre.description, " +
            "  sre.`status`, " +
            "  sre.parent_ids, " +
            "  sre.create_date, " +
            "  sre.update_date, " +
            "  sre.create_by, " +
            "  sre.update_by, " +
            "  sre.del_flag, " +
            "  sre.permission_str  " +
            "FROM " +
            "  sys_role AS sro, " +
            "  sys_role_resource AS srr, " +
            "  sys_resource AS sre  " +
            "WHERE " +
            "  sro.id = srr.role_id  " +
            "  AND srr.resource_id = sre.id  " +
            "  AND sro.del_flag = 0  " +
            "  AND srr.del_flag = 0  " +
            "  AND sre.del_flag = 0 " +
            "  and sro.id = #{rid}")
    List<SysResource> selectByRid(long rid);


    @Select("SELECT " +
            "  sre.*  " +
            "FROM " +
            "  sys_resource sre, " +
            "  sys_role_resource srr, " +
            "  sys_role sro " +
            "${ew.customSqlSegment}")
    List<SysResource> selectByRids(@Param(Constants.WRAPPER) Wrapper ew);
}
