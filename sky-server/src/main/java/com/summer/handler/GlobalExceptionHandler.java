package com.summer.handler;


import com.summer.execption.BaseException;
import com.summer.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public <T> Result<T> error(BaseException e) {
        log.error("异常信息: {}", e.getMessage());

        return Result.error(e.getMessage());
    }
}
