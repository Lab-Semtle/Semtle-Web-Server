package com.archisemtle.semtlewebserverspring.common;

import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//Swagger는 api문서는 다 들고오려는 특성이 있는데 예외 처리 컨트롤러를 가져오는 과정에서는 에럭 발생
// 이에 Hidden 어노테이션으로 swagger가 탐지하는 영역에서 벗어나게 설정

@Hidden
@RestControllerAdvice
public class GlobalExceptionContreoller {
    @ExceptionHandler(BaseException.class)
    public CommonResponse<String> BaseExceptionHandler(BaseException e){
        return CommonResponse.fail(e.getErrorCode(), e.getErrorCode().getMessage());
    }
}
