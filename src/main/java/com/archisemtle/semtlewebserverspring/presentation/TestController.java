package com.archisemtle.semtlewebserverspring.presentation;

import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1")
public class TestController {

    @GetMapping("/health")
    @Operation(summary = "서버 테스트용 API", description = "서버 테스트용 API입니다.")
    public CommonResponse<String> test() {
        return CommonResponse.success("api요청성공");
    }
}
