package com.gec.hawsteproject.hawaste.domain;

import com.gec.hawsteproject.hawaste.entity.SysOffice;
import com.gec.hawsteproject.hawaste.entity.SysResource;
import com.gec.hawsteproject.hawaste.entity.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class SysRoleDo extends SysRole {
    private String officeName;

    //角色授权资源列表
    private List<SysResource> resources;
    //跨公司授权列表
    private List<SysOffice> offices;
}
