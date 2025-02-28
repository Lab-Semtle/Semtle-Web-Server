package com.archisemtle.semtlewebserverspring.presentation;


import com.archisemtle.semtlewebserverspring.application.OwnerService;
import com.archisemtle.semtlewebserverspring.application.PromotionService;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.dto.OwnerResponseDto;
import com.archisemtle.semtlewebserverspring.dto.PromotionResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.archisemtle.semtlewebserverspring.common.BaseResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/own")
@CrossOrigin(value = "*")
public class OwnerController {

    private final OwnerService ownerService;


    @GetMapping("/promotions")
    public ResponseEntity<CommonResponse<?>> getPromotionsByOwnerId(
        @RequestParam(name = "page", defaultValue = "1", required = false) int page,
        @RequestParam(name = "size", defaultValue = "10", required = false) int size) {

            if (page < 1) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(CommonResponse.fail(WRONG_PAGE_NUM_MIN));
            }

            if (size < 1 || size > 100) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(CommonResponse.fail(WRONG_PAGE_NUM_MAX));
            }

            try{
                OwnerResponseDto responseDto = ownerService.getPromotionsByOwnerId(page, size);
                return ResponseEntity
                        .ok(CommonResponse.success(responseDto));
            }catch(Exception e){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(CommonResponse.fail(WRONG_SERVER));
            }
        }
}
