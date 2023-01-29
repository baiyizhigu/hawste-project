package com.gec.hawsteproject.hawaste.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.gec.hawsteproject.hawaste.domain.ExamineDo;
import com.gec.hawsteproject.hawaste.entity.Examine;
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
public interface ExamineMapper extends BaseMapper<Examine> {

    /**
     * 通过mybatis_plus提供的querywrapper实现动态拼接sql
     * @param page
     * @param ew  mybatis_plus调用的时候参数名要么叫ew，要么加上@Param(Constants.WRAPPER)
     *            动态sql拼接${ew.customSqlSegment}
     *            会自动生成where语句
     * @return
     */
    @Select("SELECT " +
            "    ex.id, " +
            "    ex.type, " +
            "    ex.create_date, " +
            "    ex.score, " +
            "    su.NAME user_name, " +
            "    so.NAME office_name  " +
            "FROM " +
            "    examine ex, " +
            "    sys_user su, " +
            "    sys_office so  " +
            " ${ew.customSqlSegment}")
    IPage<ExamineDo> selectByCondition(IPage<ExamineDo> page,@Param(Constants.WRAPPER) Wrapper ew);
}
