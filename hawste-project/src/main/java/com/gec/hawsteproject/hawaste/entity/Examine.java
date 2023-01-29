package com.gec.hawsteproject.hawaste.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author gec
 * @since 2021-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Examine implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long examineUserId;

    private Integer score;

    /**
     * 每一道题的分别得分详情            {'1':2;'2':1}
     */
    private String scoreInfo;

    /**
     * 1、我是产废方            2、我是处置方
     */
    private Integer type;

    /**
     * 数据创建时间,在数据新增时设置
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    /**
     * 数据修改时间,在数据新增时和修改时设置
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

    /**
     * 删除标记（0：正常；1：删除；2：审核；）
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private String delFlag;

    private String createBy;


}
