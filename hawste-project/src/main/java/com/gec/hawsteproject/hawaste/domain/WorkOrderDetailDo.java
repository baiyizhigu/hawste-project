package com.gec.hawsteproject.hawaste.domain;

import com.gec.hawsteproject.hawaste.entity.WorkOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/*
 * 工单详情Do对象
 * */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class WorkOrderDetailDo extends WorkOrder {

    private List<DetailDo> details;     //工单的详单信息
    private List<TransferDo> transfers;    //工单的转运记录
    private String  createUserName;     //产废用户名
    private String  createOfficeName;    //产废用户所在公司
    private String  createUserPhone;     //产废用户电话
    private String  transportUserName;   //运输用户信息
    private String  transportOfficeName;
    private String  transportUserPhone;
    private String  recipientUserName;   //处置用户信息
    private String  recipientOfficeName;
    private String  recipientUserPhone;
}
