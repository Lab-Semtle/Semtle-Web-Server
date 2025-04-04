package com.archisemtle.semtlewebserverspring.common;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class ExceptHandler {


    @ExceptionHandler(BaseException.class)
    protected CommonResponse<String> handlerCustomException(BaseException e){
        return CommonResponse.fail(e.getErrorCode());
    }


}
