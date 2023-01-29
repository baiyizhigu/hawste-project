package com.gec.hawsteproject.hawaste.domain;

import com.gec.hawsteproject.hawaste.entity.Examine;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * DO(Domain Object)对象，领域对象   对应数据库查询返回的业务实体对象
 * 目的：
 * 对应前端的展示的对象  抽取出来的Do，所以叫ExamineDo
 * 需要与数据库查询结果对应，通常也是一个javabean
 *
 */
@Data
//默认只显示本来的属性
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper = true)
public class ExamineDo extends Examine implements Serializable {
    private String userName;//用户名
    private String officeName;//企业名
}
