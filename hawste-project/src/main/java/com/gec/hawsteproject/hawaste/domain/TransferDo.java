package com.gec.hawsteproject.hawaste.domain;

import com.gec.hawsteproject.hawaste.entity.Transfer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/*
 * 转运记录Do对象
 * */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class TransferDo extends Transfer {
    private String  userName;     //转运用户姓名
    private String  userPhone;    //转运用户电话

}
