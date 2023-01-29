package com.gec.hawsteproject.hawaste.domain;

import com.gec.hawsteproject.hawaste.entity.Qualification;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode
public class QualificationDo extends Qualification implements Serializable {

    private String uploadUserName;
    private String checkUserName;

}
