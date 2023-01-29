package com.gec.hawsteproject.hawaste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.hawsteproject.hawaste.domain.ExamineDo;
import com.gec.hawsteproject.hawaste.entity.Examine;
import com.gec.hawsteproject.hawaste.mapper.ExamineMapper;
import com.gec.hawsteproject.hawaste.service.IExamineService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
@Service
public class ExamineServiceImpl extends ServiceImpl<ExamineMapper, Examine> implements IExamineService {


    /**
     * 根据动态条件分页查询examine，返回分页对象
     * @param page      分页对象
     * @param params    动态查询条件
     * @return          分页动态条件查询结果
     */
    @Override
    public IPage<ExamineDo> selectByCondition(IPage<ExamineDo> page, Map<String,Object> params){
        //1.查询包装对象
        QueryWrapper<ExamineDo> queryWrapper = new QueryWrapper<>();
        //2.拼接where条件
        //apply()拼接sql语句，底层通过预编译处理 先拼接关联条件
        queryWrapper
                .apply("ex.del_flag = 0 AND ex.examine_user_id = su.id AND su.office_id = so.id ")
                /**
                 * AND ex.type = 1
                 * 	AND so.id = 56
                 * 	AND su.NAME LIKE CONCAT( '%', '工作', '%' )
                 */
                //动态sql拼接，如果第一个条件false则不生成拼接sql
                .eq(params.containsKey("type")&& !ObjectUtils.isEmpty(params.get("type")),
                        "ex.type",
                        params.get("type"))
                .eq(params.containsKey("officeId")&& !ObjectUtils.isEmpty(params.get("officeId")),
                        "so.id",
                        params.get("officeId"))
                .like(params.containsKey("name")&& !ObjectUtils.isEmpty(params.get("name")),
                        "su.NAME",
                        params.get("name"));
        System.out.println(queryWrapper.getTargetSql());//获取拼接后的sql
        /**
         * 在ServiceImpl中有
         * @Autowired
         *     protected M baseMapper;
         *     进行了注入Mapper层对象，使用spring的泛型注入,baseMapper实际是ExamineMapper
         */
        return baseMapper.selectByCondition(page,queryWrapper);
    }
}
