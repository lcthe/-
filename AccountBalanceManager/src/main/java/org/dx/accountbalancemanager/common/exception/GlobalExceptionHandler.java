package org.dx.accountbalancemanager.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ApiResponse<String> handleGeneralException(Exception e) {
        log.error("General Exception caught: {}", e.getMessage());
        return ApiResponse.error(String.valueOf(CommonEnum.INTERNAL_SERVER_ERROR.getResultCode()), "内部服务器错误", null);
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    @ResponseBody
    public ApiResponse<String> bindExceptionHandler(Exception e) {
        StringBuilder errorInfo = new StringBuilder();
        BindingResult bindingResult = null;
        if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        }
        if (e instanceof BindException) {
            bindingResult = ((BindException) e).getBindingResult();
        }
        if (bindingResult != null && bindingResult.hasFieldErrors()) {
            for (int i = 0; i < Objects.requireNonNull(bindingResult).getFieldErrors().size(); i++) {
                if (i > 0) {
                    errorInfo.append(",");
                }
                FieldError fieldError = bindingResult.getFieldErrors().get(i);
                errorInfo.append(fieldError.getField()).append(" :").append(fieldError.getDefaultMessage());
            }
        }else {
            errorInfo.append(e.getMessage());
        }

        log.error("errorInfo:{}", errorInfo);
        return ApiResponse.error(String.valueOf(CommonEnum.INTERNAL_SERVER_ERROR.getResultCode()),
                String.format("参数校验失败:%s", errorInfo), null);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    public ApiResponse<String> handleIllegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("IllegalArgumentException caught: {}", e.getMessage());
        return ApiResponse.error(String.valueOf(CommonEnum.INTERNAL_SERVER_ERROR.getResultCode()),
                String.format("IllegalArgumentException: %s", e.getMessage()), null);
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public ApiResponse<String> handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException caught: {}", e.getMessage());
        return ApiResponse.error(String.valueOf(CommonEnum.INTERNAL_SERVER_ERROR.getResultCode()), "运行时异常", null);
    }
}
