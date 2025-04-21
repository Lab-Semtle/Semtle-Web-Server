package com.archisemtle.semtlewebserverspring.presentation.archive;

import com.archisemtle.semtlewebserverspring.application.archive.ArchiveService;
import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveListRequestDto;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveListResponseDto;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveRequestDto;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveResponseDto;
import com.archisemtle.semtlewebserverspring.vo.archive.ArchiveListRequestVo;
import com.archisemtle.semtlewebserverspring.vo.archive.ArchiveListResponseVo;
import com.archisemtle.semtlewebserverspring.vo.archive.ArchiveRequestVo;
import com.archisemtle.semtlewebserverspring.vo.archive.ArchiveResponseVo;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/archive")
public class ArchiveController {

    private static final Logger log = LoggerFactory.getLogger(ArchiveController.class);
    private final ArchiveService archiveService;

    //게시물 생성
    @PostMapping
    public CommonResponse<String> createArchive(@RequestBody ArchiveRequestVo requestVo) throws IOException{
        archiveService.createArchiveBoard(ArchiveRequestVo.voToDto(requestVo));

        return CommonResponse.success("Secret Note 게시물이 성공적으로 등록되었습니다.");
    }

    //게시물 읽기
    @GetMapping("/{id}")
    public CommonResponse<ArchiveResponseVo> readArchive(@PathVariable Long id){
        ArchiveResponseDto responseDto = archiveService.readArchiveBoard(id);

        return CommonResponse.success("족보 게시판 흭득 성공", ArchiveResponseDto.dtoToVo(responseDto));
    }

    //게시물 수정
    @PutMapping("/{id}")
    public CommonResponse<String> updateArchive(@RequestBody ArchiveRequestVo requestVo, @PathVariable Long id)
    throws IOException {

        archiveService.updateArchiveBoard(id, ArchiveRequestVo.voToDto(requestVo));
        return CommonResponse.success("족보 게시판 수정 성공");
    }

    //게시물 삭제
    @DeleteMapping("/{id}")
    public CommonResponse<String> deleteArchive(@PathVariable Long id){
        archiveService.deleteArchiveBoard(id);
        return CommonResponse.success("족보 게시판 삭제 성공");
    }

    //게시물 목록 읽기
    @GetMapping
    public CommonResponse<ArchiveListResponseVo> getArchiveList(
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "size", defaultValue = "15") int size,
        @RequestParam(name = "search_keyword", defaultValue = "") String search_keyword
    ){
        if(page < 1 || size < 1) throw new BaseException(BaseResponseStatus.WRONG_PARAM);
        ArchiveListRequestVo requestVo = new ArchiveListRequestVo(page, size, search_keyword);
        ArchiveListRequestDto requestDto = ArchiveListRequestVo.voToDto(requestVo);
        ArchiveListResponseDto responseDto = archiveService.getArchiveList(requestDto);
        return CommonResponse.success("족보 게시판 목록 얻어오기 성공", ArchiveListResponseDto.dtoToVo(responseDto));

    }


}
