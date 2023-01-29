package com.gec.hawsteproject.hawaste.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.hawsteproject.hawaste.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 1.查询角色已分配人员
     */
    @Select("SELECT " +
            " su.id, " +
            " su.NAME  " +
            "FROM " +
            " sys_role sr, " +
            " sys_user_role sur, " +
            " sys_user su  " +
            "WHERE " +
            " sr.del_flag = 0  " +
            " AND su.del_flag = 0  " +
            " AND sur.del_flag = 0 " +
            " AND sr.id = sur.role_id  " +
            " AND sur.user_id = su.id  " +
            " AND sr.id =#{rid}")
    List<SysUser> selectByRid(long rid);

    /**
     * 2.找出公司未分配指定角色的人员
     */
    @Select("SELECT " +
            "  *  " +
            "FROM " +
            "  sys_user  " +
            "WHERE " +
            "  office_id = #{oid}  " +
            "  AND del_flag = 0  " +
            "  AND id NOT IN ( " +
            "SELECT " +
            "  su.id " +
            "FROM " +
            "  sys_role sr, " +
            "  sys_user_role sur, " +
            "  sys_user su  " +
            "WHERE " +
            "  sr.del_flag = 0  " +
            "  AND su.del_flag = 0  " +
            "  AND sur.del_flag = 0 "+
            "  AND sr.id = sur.role_id  " +
            "  AND sur.user_id = su.id  " +
            "  AND sr.id = #{rid}  " +
            "  ) " +
            " ")
    List<SysUser> selectNoRole(@Param("rid") long rid,@Param("oid") long oid);
}
