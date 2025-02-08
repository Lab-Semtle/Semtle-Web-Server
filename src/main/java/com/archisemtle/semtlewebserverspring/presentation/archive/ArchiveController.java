package com.archisemtle.semtlewebserverspring.presentation.archive;

import com.archisemtle.semtlewebserverspring.application.archive.ArchiveService;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveRequestDto;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveResponseDto;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/archive")
public class ArchiveController {

    private final ArchiveService archiveService;

    @PostMapping
    public CommonResponse<String> createArchive(@RequestBody ArchiveRequestDto requestDto) throws IOException{
        archiveService.createArchiveBoard(requestDto);
        return CommonResponse.success("족보 게시판 생성 성공");
    }

    @GetMapping("/{id}")
    public CommonResponse<ArchiveResponseDto> readArchive(@PathVariable Long id){
        ArchiveResponseDto responseDto = archiveService.readArchiveBoard(id);
        return CommonResponse.success("족보 게시판 흭득 성공", responseDto);
    }

    @PutMapping("/{id}")
    public CommonResponse<String> updateArchive(@RequestBody ArchiveRequestDto requestDto, @PathVariable Long id)
    throws IOException {

        archiveService.updateArchiveBoard(id, requestDto);
        return CommonResponse.success("족보 게시판 수정 성공");
    }

    @DeleteMapping("/{id}")
    public CommonResponse<String> deleteArchive(@PathVariable Long id){
        archiveService.deleteArchiveBoard(id);
        return CommonResponse.success("족보 게시판 삭제 성공");
    }


}
