package com.summer.handler;


import com.summer.constant.MessageConstant;
import com.summer.execption.BaseException;
import com.summer.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public <T> Result<T> error(BaseException e) {
        log.error("异常信息: {}", e.getMessage());

        return Result.error(e.getMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public <T> Result<T> exceptionHandler(SQLIntegrityConstraintViolationException e) {

        String message = e.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            String username = split[2];

            String msg = username + MessageConstant.ALREADY_EXISTS;

            return Result.error(msg);
        }

        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }
}
