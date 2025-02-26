package com.archisemtle.semtlewebserverspring.presentation;


import com.archisemtle.semtlewebserverspring.application.OwnerService;
import com.archisemtle.semtlewebserverspring.application.PromotionService;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.common.utils.UserUtils;
import com.archisemtle.semtlewebserverspring.dto.OwnerResponseDto;
import com.archisemtle.semtlewebserverspring.dto.PromotionResponseDto;
import com.archisemtle.semtlewebserverspring.vo.OwnPromotionVo;
import com.archisemtle.semtlewebserverspring.vo.PromotionVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.archisemtle.semtlewebserverspring.common.BaseResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/own")
public class OwnerController {

    private final OwnerService ownerService;

    private final UserUtils userUtils;



    @GetMapping("/promotions")
    public ResponseEntity<CommonResponse<?>> getPromotionsByOwnerId(
            @RequestHeader("Authorization") String authorizationHeader,
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
                String userUuid = userUtils.getUserUuid(authorizationHeader);
                OwnerResponseDto responseDto = ownerService.getPromotionsByOwnerId(page, size, userUuid);
                //리턴문 Dto -> Vo 변경 0226
                OwnPromotionVo responseVo = OwnPromotionVo.dtoToVo(responseDto);
                return ResponseEntity
                        .ok(CommonResponse.success(responseVo));
            }catch(Exception e){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(CommonResponse.fail(WRONG_SERVER));
            }
        }
}
