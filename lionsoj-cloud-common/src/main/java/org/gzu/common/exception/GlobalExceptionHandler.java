package org.gzu.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.gzu.common.common.BaseResponse;
import org.gzu.common.common.ErrorCode;
import org.gzu.common.common.ResultUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Classname: GlobalExceptionHandler
 * @Description: 全局异常处理器
 * @Author: lions
 * @Datetime: 12/28/2023 11:42 PM
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "系统错误");
    }
}
