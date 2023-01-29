package com.gec.hawsteproject.hawaste.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.hawsteproject.hawaste.domain.SysAreaDo;
import com.gec.hawsteproject.hawaste.entity.SysArea;
import com.gec.hawsteproject.hawaste.mapper.SysAreaMapper;
import com.gec.hawsteproject.hawaste.service.ISysAreaService;
import com.gec.hawsteproject.hawaste.utils.LocalDateTimeConverter;
import com.gec.hawsteproject.hawaste.utils.SysAreaListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * <p>
 * 区域表 服务实现类
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
@Service
public class SysAreaServiceImpl extends ServiceImpl<SysAreaMapper, SysArea> implements ISysAreaService {

    @Autowired
    LocalDateTimeConverter localDateTimeConverter;


    /**
     * 根据动态条件查询列表并分页
     * @param page      分页对象
     * @param params    参数条件：
     *                  1.根据区域名查询区域信息
     *                  2.根据区域id查询其所有子级区域 (前端传值保证二选一)
     * @return
     */
    @Override
    public IPage< SysAreaDo> selectByCondition(IPage< SysAreaDo> page, Map<String, Object> params){
        QueryWrapper< SysAreaDo> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("sub.del_flag","0")
                .like(params.containsKey("name")&&!ObjectUtils.isEmpty(params.get("name")),
                        "sub.NAME",
                        params.get("name"))
                //为了避免层级关系的逻辑问题，需要在参数前后加","
                .like(params.containsKey("id")&&!ObjectUtils.isEmpty(params.get("id")),
                        "sub.parent_ids",
                        ","+params.get("id")+",");
        return baseMapper.selectByCondition(page,queryWrapper);
    }

    /**
     * 根据id删除该区域和其子区域
     * @param id
     */
    @Override
    public void removeAreaAndSub(Long id){
        UpdateWrapper<SysArea> wrapper = new UpdateWrapper<>();
        wrapper
                .set("del_flag",1)
                .eq("id",id)
                .or()
                .like("parent_ids",","+id+",");
        baseMapper.delete(wrapper);
    }


    @Override
    public SysAreaDo selectAreaById(Long id){
//        return baseMapper.selectAreaById(id);
        SysAreaDo sysAreaDo = baseMapper.selectAreaById(id);
        //未更新前，新旧ids一致
        sysAreaDo.setOldParentIds(sysAreaDo.getParentIds());
        return sysAreaDo;
    }


    /**
     * 更新区域功能：
     * 1.更新本区域
     * 2.更新本区域下的所有子区域的parent_ids
     * @param sysAreaDo
     */
    @Override
    @Transactional  //多个更新  一起完成
    public void updateAreaAndSub(SysAreaDo sysAreaDo){
        // 1.更新本区域
        baseMapper.updateById(sysAreaDo);
        // 2.更新本区域下的所有子区域的parent_ids
//        SysAreaDo sysAreaDo = (SysAreaDo) sysArea;
        //判断不一致，说明需要更新
        if(!sysAreaDo.getOldParentIds().equals(sysAreaDo.getParentIds())){
            baseMapper.updateParentIds(sysAreaDo);
        }
    }

    /**
     * 导出excel
     * 1.查询数据库中所有的区域信息
     * 2.根据easyexcel的api将java对象转换成excel输出流对象
     * @param outputStream  响应输出流对象，从response中获取
     */
    @Override
    public void exportExcel(OutputStream outputStream){
        EasyExcel
                .write(outputStream, SysArea.class)//将对象读取后放到excel输出流对象中
                .sheet()
                .registerConverter(localDateTimeConverter)//自定义LocalDateTime转换
                .doWrite(baseMapper.selectList(null));
    }

    /**
     * 根据传入的excel文件流，解析出java对象集合，批量(10条)插入到数据库中
     * @param inputStream
     */
    @Override
    @Transactional   //多次批量插入一起完成
    public void importExcel(InputStream inputStream){
        EasyExcel
                .read(inputStream,SysArea.class,new SysAreaListener(this))
                .sheet()
                .registerConverter(localDateTimeConverter)
                .doRead();
    }

}
