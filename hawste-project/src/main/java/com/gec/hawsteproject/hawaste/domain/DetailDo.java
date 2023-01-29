package com.gec.hawsteproject.hawaste.domain;

import com.gec.hawsteproject.hawaste.entity.Detail;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/*
 * 工单详单Do对象
 * */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class DetailDo extends Detail {
    private String  wasteTypeCode;     //危废类型编码
    private String wasteTypeName;    //危废类型名
    private String wasteCode;   //危废小类编码
}
