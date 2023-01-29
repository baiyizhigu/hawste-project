package com.gec.hawsteproject.hawaste.domain;

import com.gec.hawsteproject.hawaste.entity.SysArea;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 区域列表DO对象
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode
public class SysAreaDo extends SysArea {
    private String parentName;//父区域名称
    /**
     * 旧的父区域ids  用于保存旧的层级结构关系
     */
    private String oldParentIds;
}
