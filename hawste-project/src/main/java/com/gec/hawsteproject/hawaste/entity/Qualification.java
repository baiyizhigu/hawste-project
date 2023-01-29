package com.gec.hawsteproject.hawaste.entity;

import com.baomidou.mybatisplus.annotation.*;
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
public class Qualification implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long uploadUserId;

    /**
     * 1、我是产废方            2、我是运输方            3、我是处置方
     */
    private Integer type;

    private String address;

    /**
     * 0未审核            1通过审核            2审核失败
     */
    @TableField("`check`")  //将该属性转换成sql语句字段的映射设置为`check`
    private Integer check;

    private String description;

    private Long checkUserId;

    @TableField(fill = FieldFill.INSERT)
    /*@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    LocalDateTime转json格式化*/
    /*json转换java的LocalDateTime
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")*/
    private LocalDateTime createDate;

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
