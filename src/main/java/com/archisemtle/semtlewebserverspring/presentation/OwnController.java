package com.archisemtle.semtlewebserverspring.presentation;

import static com.archisemtle.semtlewebserverspring.common.BaseResponseStatus.WRONG_PAGE_NUM_MAX;
import static com.archisemtle.semtlewebserverspring.common.BaseResponseStatus.WRONG_PAGE_NUM_MIN;
import static com.archisemtle.semtlewebserverspring.common.BaseResponseStatus.WRONG_SERVER;

import com.archisemtle.semtlewebserverspring.application.OwnerService;
import com.archisemtle.semtlewebserverspring.application.archive.ArchiveService;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.dto.OwnerResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectBoardListDto;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveListResponseDto;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.ProjectBoardRepositoryImpl;
import com.archisemtle.semtlewebserverspring.vo.ProjectBoardPageResponseVo;
import com.archisemtle.semtlewebserverspring.vo.archive.ArchiveListResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/own")
@RequiredArgsConstructor
public class OwnController {

    private final ArchiveService archiveService;
    private final OwnerService ownerService;
    private final ProjectBoardRepositoryImpl projectBoardRepository;

    @GetMapping("/archives")
    @Operation(summary = "자신이 올린 족보목록을 조회하는 API", description = "자신이 올린 족보목록을 조회하는 API입니다.")
    public CommonResponse<ArchiveListResponseVo> getOwnArchiveList(
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "uuid") UUID uuid) {

        return CommonResponse.success("내가 작성한 족보 목록을 성공적으로 불러왔습니다.",
            ArchiveListResponseDto.dtoToVo(archiveService.getOwnArchiveList(page, size, uuid)));

    }

    @GetMapping("/promotions")
    @Operation(summary = "자신이 올린 프로젝트홍보목록을 조회하는 API", description = "자신이 올린 프로젝트홍보목록을 조회하는 API입니다.")
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

        try {
            OwnerResponseDto responseDto = ownerService.getPromotionsByOwnerId(page, size);
            return ResponseEntity
                .ok(CommonResponse.success(responseDto));
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.fail(WRONG_SERVER));
        }
    }

    @GetMapping("/mywritingprojectboard")
    @Operation(summary = "자신이 올린 포르젝트 모집 목록을 조회하는 API", description = "자신이 올린 프로젝트 모집 목록을 조회하는 API입니다.")
    public CommonResponse<Page<ProjectBoardListDto>> getMyWritingProjectBoard(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID uuid = UUID.fromString(authentication.getName());
        Pageable pageable = PageRequest.of(page, size);

        return CommonResponse.success(projectBoardRepository.myProjectBoardPage(uuid, pageable));
    }
}
