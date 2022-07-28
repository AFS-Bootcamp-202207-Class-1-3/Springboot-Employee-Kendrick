package com.rest.springbootemployee.advice;

import com.rest.springbootemployee.exception.NotFoundOneException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author KENDRICK
 * @Mail KENDRICK.CHEN@OOCL.COM
 * @Date 2022/7/27 20:10
 */
@RestControllerAdvice
public class GlobalControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundOneException.class})
    public ErrorResponse handlerNotFoundException(Exception exception){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(),exception.getMessage());
    }
}
