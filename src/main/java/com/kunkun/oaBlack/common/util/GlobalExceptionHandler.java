package com.kunkun.oaBlack.common.util;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局拦截类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BizException.class)
    @RequestBody
    public ResultUtil bizExceptionHandler(HttpServletRequest request,BizException e){
        logger.error("发生业务异常！原因是：{}",e.getCause());
        return ResultUtil.faile(e.getErrorCode(),e.getErrorMsg());
    }

    @ExceptionHandler(value = Exception.class)
    @RequestBody
    public ResultUtil exceptionHandler(HttpServletRequest request,BizException e){
        logger.error("发生未知异常，原因是：{}",e);
        return ResultUtil.faile(CodeUtil.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = BizException.class)
    @RequestBody
    public ResultUtil bizExceptionHandler(BizException e){
        logger.error("发生业务异常！原因是：{}",e.getCause());
        return ResultUtil.faile(e.getErrorCode(),e.getErrorMsg());
    }
}
