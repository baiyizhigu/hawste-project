package com.gec.hawsteproject.hawaste.domain;

import com.gec.hawsteproject.hawaste.entity.WorkOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@ToString(callSuper = true)
@EqualsAndHashCode
public class WorkOrderDo extends WorkOrder {

    private String createUserName;
    private  String createOfficeName;
    private String transportUserName;
    private String recipientUserName;

}
