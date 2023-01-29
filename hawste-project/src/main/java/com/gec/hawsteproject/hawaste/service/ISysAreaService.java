package com.gec.hawsteproject.hawaste.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawsteproject.hawaste.domain.SysAreaDo;
import com.gec.hawsteproject.hawaste.entity.SysArea;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * <p>
 * 区域表 服务类
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
public interface ISysAreaService extends IService<SysArea> {

    IPage<SysAreaDo> selectByCondition(IPage<SysAreaDo> page, Map<String, Object> params);

    void removeAreaAndSub(Long id);

    SysAreaDo selectAreaById(Long id);

    void updateAreaAndSub(SysAreaDo sysArea);

    void exportExcel(OutputStream outputStream);

    void importExcel(InputStream inputStream);
}
