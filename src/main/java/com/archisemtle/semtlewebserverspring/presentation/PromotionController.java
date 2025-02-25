package com.archisemtle.semtlewebserverspring.presentation;


import com.archisemtle.semtlewebserverspring.application.PromotionService;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.domain.ProjectBoard;
import com.archisemtle.semtlewebserverspring.dto.*;
import com.archisemtle.semtlewebserverspring.infrastructure.PromotionRepository;
import com.archisemtle.semtlewebserverspring.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.archisemtle.semtlewebserverspring.common.BaseResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/promotions")
public class PromotionController {

    private final PromotionService promotionService;
    private final PromotionRepository promotionRepository;

    @GetMapping("")
    public ResponseEntity<CommonResponse<?>> getPromotions(
            @RequestParam(name = "keyword",  required = false) String keyword,
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
            PromotionResponseDto responseDto = promotionService.getPromotions(keyword, page, size);
            return ResponseEntity
                    .ok(CommonResponse.success(responseDto));
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CommonResponse.fail(WRONG_SERVER));
        }
    }

    @GetMapping("/{promotionId}")
    public ResponseEntity<CommonResponse<?>> getPromotionsById(
            @PathVariable("promotionId") Long id) {
        if(id == null || id<0){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(CommonResponse.fail(WRONG_PARAM));
        }
        try{
            ProjectBoardResponseDto2 responseDto = promotionService.getPromotionsById(id);
            if(responseDto == null){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(CommonResponse.fail(NONE_DATA));
            }else{
                return ResponseEntity
                        .ok(CommonResponse.success(responseDto));
            }
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CommonResponse.fail(WRONG_SERVER));
        }
    }

    @PostMapping("")
    public ResponseEntity<CommonResponse<?>> createPromotion(
            @RequestBody ProjectBoardRequestDto reqDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CommonResponse.fail(WRONG_PARAM,errorMessage));
        }
        try{
            ProjectBoardCUDResponseDto response = promotionService.mergePromotion(reqDto);
            PromotionCUDDtos.Create responseDto = new PromotionCUDDtos.Create(
                    "프로젝트 홍보 게시물이 성공적으로 등록되었습니다"
                    ,response.getBoardId()
                    ,response.getCreateDt());
            return ResponseEntity
                    .ok(CommonResponse.success(responseDto));
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CommonResponse.fail(WRONG_SERVER));
        }
    }

    @PatchMapping("/{promotionId}")
    public ResponseEntity<CommonResponse<?>> updatePromotion(
            @RequestBody ProjectBoardRequestDto reqDto,
            @PathVariable("promotionId") Long id,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CommonResponse.fail(WRONG_PARAM,errorMessage));
        }
        if(id == null || id<0){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(CommonResponse.fail(WRONG_PARAM));
        }
        try{
            ProjectBoardResponseDto2 checkPromotion = promotionService.getPromotionsById(id);
            if(checkPromotion == null){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(CommonResponse.fail(NOT_FOUND_DATA));
            }

            reqDto.setBoardId(id);
            ProjectBoardCUDResponseDto response = promotionService.mergePromotion(reqDto);
            PromotionCUDDtos.Update responseDto = new PromotionCUDDtos.Update(
                    "프로젝트 홍보 게시물이 성공적으로 수정되었습니다"
                    ,response.getUpdateDt());
            return ResponseEntity
                    .ok(CommonResponse.success(responseDto));
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CommonResponse.fail(WRONG_SERVER));
        }
    }

    @DeleteMapping("/{promotionId}")
    public ResponseEntity<CommonResponse<?>> deletePromotion(
            @PathVariable("promotionId") Long id) {
        if(id == null || id<0){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(CommonResponse.fail(WRONG_PARAM));
        }
        try{
            ProjectBoardResponseDto2 checkPromotion = promotionService.getPromotionsById(id);
            if(checkPromotion == null){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(CommonResponse.fail(NONE_DATA));
            }

            ProjectBoardCUDResponseDto response = promotionService.deletePromotion(id);
            PromotionCUDDtos.Update responseDto = new PromotionCUDDtos.Update(
                    "프로젝트 홍보 게시물이 성공적으로 삭제되었습니다"
                    ,response.getUpdateDt());
            return ResponseEntity
                    .ok(CommonResponse.success(responseDto));
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CommonResponse.fail(WRONG_SERVER));
        }
    }
}
