package com.gec.hawsteproject.hawaste.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 区域表
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)

public class SysArea implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父级编号
     */
    private Long parentId;

    /**
     * 所有父级编号
     */
    private String parentIds;

    /**
     * 区域编码
     */
    @ExcelProperty("区域编码")
    private String code;

    /**
     * 区域名称
     */
    private String name;

    /**
     * 区域类型
     */
    private String type;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    /**
     * 设置excel的列和属性的对应映射关系
     * value:设置表头名字
     * index:设置列的位置
     */
    @ExcelProperty
    @DateTimeFormat("yyyy年MM月dd日")//easyexcel日期格式化注解 只支持Date和DateTime
    private LocalDateTime createDate;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

    /**
     * 备注信息
     */
    private String remarks;

    /**
     * 删除标记(0活null 正常 1,删除)
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    @ExcelIgnore  //忽略生成到excel中
    private String delFlag;

    private String icon;


}
