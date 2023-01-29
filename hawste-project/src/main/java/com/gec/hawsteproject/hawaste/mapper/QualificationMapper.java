package com.gec.hawsteproject.hawaste.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.gec.hawsteproject.hawaste.domain.QualificationDo;
import com.gec.hawsteproject.hawaste.entity.Qualification;
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
public interface QualificationMapper extends BaseMapper<Qualification> {

    /**
     * 通过mybatis_plus提供的querywrapper实现动态拼接sql
     * @param page
     * @param ew  mybatis_plus调用的时候参数名要么叫ew，要么加上@Param(Constants.WRAPPER)
     *            动态sql拼接${ew.customSqlSegment}
     *            会自动生成where语句
     * @return
     */
    @Select("SELECT " +
            "  qu.id, " +
            "  qu.type, " +
            "  qu.`check`, " +
            "  qu.description, " +
            "  qu.create_date, " +
            "  qu.update_date, " +
            "  uu.NAME upload_user_name, " +
            "  cu.NAME check_user_name  " +
            "FROM " +
            "  qualification qu " +
            "  LEFT JOIN sys_user uu ON qu.upload_user_id = uu.id " +
            "  LEFT JOIN sys_user cu ON qu.check_user_id = cu.id " +
            " ${ew.customSqlSegment}")
    IPage<QualificationDo> selectByCondition(IPage<QualificationDo> page, @Param(Constants.WRAPPER) Wrapper ew);
}
