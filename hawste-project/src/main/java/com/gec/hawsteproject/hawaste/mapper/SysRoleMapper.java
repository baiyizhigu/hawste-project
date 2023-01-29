package com.gec.hawsteproject.hawaste.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.gec.hawsteproject.hawaste.domain.SysRoleDo;
import com.gec.hawsteproject.hawaste.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 通过mybatis_plus提供的querywrapper实现动态拼接sql
     * @param page
     * @param ew  mybatis_plus调用的时候参数名要么叫ew，要么加上@Param(Constants.WRAPPER)
     *            动态sql拼接${ew.customSqlSegment}
     *            会自动生成where语句
     * @return
     */
    @Select("SELECT " +
            "  sr.*, " +
            "  so.NAME office_name  " +
            "FROM " +
            "  sys_role sr, " +
            "  sys_office so " +
            " ${ew.customSqlSegment}")
    IPage<SysRoleDo> selectByCondition(IPage<SysRoleDo> page, @Param(Constants.WRAPPER) Wrapper ew);


    /**
     * 根据角色id查询角色信息和公司名
     * @param rid  角色id
     * @return     角色信息和公司名
     */
    @Select("SELECT " +
            "  sr.*, " +
            "  so.NAME office_name  " +
            "FROM " +
            "  sys_role sr, " +
            "  sys_office so  " +
            "WHERE " +
            "  sr.del_flag = 0  " +
            "  and so.del_flag = 0 " +
            "  AND sr.office_id = so.id  " +
            "  AND sr.id = #{rid}")
    SysRoleDo selectByRid(long rid);


    /*
    * 根据用户id查询其角色信息
    * */
    @Select("SELECT " +
            "  sr.*  " +
            "FROM " +
            "  sys_role sr, " +
            "  sys_user_role sur, " +
            "  sys_user su  " +
            "WHERE " +
            "  sr.id = sur.role_id  " +
            "  AND sur.user_id = su.id  " +
            "  AND sr.del_flag = 0  " +
            "  AND sur.del_flag = 0  " +
            "  AND su.del_flag = 0  " +
            "  AND su.id =#{id}")
    List<SysRole> selectByUid(Long id);
}
