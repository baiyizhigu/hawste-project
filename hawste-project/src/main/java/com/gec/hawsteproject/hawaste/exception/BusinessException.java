package com.gec.hawsteproject.hawaste.exception;

import com.gec.hawsteproject.hawaste.entity.ResponseStatus;
import lombok.Data;

/**
 * 自定义业务处理异常，用于处理用户请求，业务错误的时候抛出的异常
 */
@Data  //设置get、set、toString等
public class BusinessException extends RuntimeException{
    private ResponseStatus responseStatus;

    public BusinessException (ResponseStatus responseStatus){
        //调用运行时异常的构造方法
        super(responseStatus.getMsg(),null,false,false);
        this.responseStatus = responseStatus;
    }
}
