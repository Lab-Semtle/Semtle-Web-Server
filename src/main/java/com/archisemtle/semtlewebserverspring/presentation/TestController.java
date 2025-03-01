package com.archisemtle.semtlewebserverspring.presentation;

import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1")
@CrossOrigin(value = "*")
public class TestController {

    @GetMapping("/health")
    public CommonResponse<String> test() {
        return CommonResponse.success("api요청성공");
    }
}
