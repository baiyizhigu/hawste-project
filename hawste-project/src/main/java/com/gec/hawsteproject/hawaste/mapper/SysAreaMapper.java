package com.gec.hawsteproject.hawaste.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.gec.hawsteproject.hawaste.domain.SysAreaDo;
import com.gec.hawsteproject.hawaste.entity.SysArea;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 区域表 Mapper 接口
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
public interface SysAreaMapper extends BaseMapper<SysArea> {

    /**
     * 通过mybatis_plus提供的querywrapper实现动态拼接sql
     * @param page
     * @param ew  mybatis_plus调用的时候参数名要么叫ew，要么加上@Param(Constants.WRAPPER)
     *            动态sql拼接${ew.customSqlSegment}
     *            会自动生成where语句
     * @return
     */
    @Select("SELECT " +
            "  sub.*, " +
            "  parent.NAME parent_name  " +
            "FROM " +
            "  sys_area sub " +
            "  LEFT JOIN sys_area parent ON sub.parent_id = parent.id " +
            " ${ew.customSqlSegment}")
    IPage<SysAreaDo> selectByCondition(IPage<SysAreaDo> page, @Param(Constants.WRAPPER) Wrapper ew);

    @Select("SELECT " +
            "  sub.*, " +
            "  parent.NAME parent_name  " +
            "FROM " +
            "  sys_area sub " +
            "  LEFT JOIN sys_area parent ON sub.parent_id = parent.id  " +
            "WHERE " +
            "  sub.del_flag = 0  " +
            "and " +
            "  sub.id = #{id}")
    SysAreaDo selectAreaById(Long id);


    /**
     * #更新本区域的所有子级区域的parent_ids
     * #replace(str,from,to)  替换(需要替换的字段,原字符串，新字符串)
     * #新字符串：父区域的parent_ids+父区域的id+","
     * @param sysArea
     */
    @Update("UPDATE sys_area  " +
            "SET parent_ids = REPLACE ( parent_ids, #{oldParentIds}, #{parentIds}) " +
            "WHERE " +
            "  parent_ids LIKE concat( '%,', #{id}, ',%' )")
    void updateParentIds(SysArea sysArea);
}
