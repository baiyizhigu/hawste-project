package com.gec.hawsteproject.hawaste.controller.advice;

import com.gec.hawsteproject.hawaste.entity.ResponseStatus;
import com.gec.hawsteproject.hawaste.entity.ResultBean;
import com.gec.hawsteproject.hawaste.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理器
 *
 * @RestControllerAdvice： controller的切面处理类，统一以json返回
 */
@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    /**
     * 框架校验数据异常
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ResultBean bindExceptionHandler(BindException e){
        log.error(e.getMessage(),e);
        return ResultBean.fail(ResponseStatus.PARAM_ERROR);
    }

    /**
     * 业务处理异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResultBean businessExceptionHandler(BusinessException e){
        log.error(e.getMessage(),e);
        return ResultBean.fail(e.getResponseStatus());
    }

    /**
     * 系统错误
     */
    @ExceptionHandler(Exception.class)
    public ResultBean exceptionHandler(Exception e){
        log.error(e.getMessage(),e);
        ResultBean resultBean = ResultBean.error();
        if(e instanceof UnauthorizedException){
            resultBean.setMsg("没有访问权限，请联系管理员");
        }else {
            resultBean.setMsg(e.getMessage());
        }
        return resultBean;
    }

}
