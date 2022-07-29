package com.github.baymin.fileserver.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;

/**
 * 全局异常处理类
 *
 * @author Zongwei
 * @date 2020/4/14 14:39
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorInfo handleFileNotFoundException(FileNotFoundException e) {
        log.error("FileNotFoundException occurred", e);
        return new ErrorInfo("not_found", e.getMessage());
    }

    @ExceptionHandler(DfsServerException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo handleDfsServerException(DfsServerException e) {
        log.error("DfsServerException occurred", e);
        return new ErrorInfo("server_error", e.getMessage());
    }

}
