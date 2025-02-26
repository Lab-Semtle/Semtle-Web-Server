package com.archisemtle.semtlewebserverspring.presentation;

import com.archisemtle.semtlewebserverspring.application.archive.ArchiveService;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveListResponseDto;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveResponseDto;
import com.archisemtle.semtlewebserverspring.vo.archive.ArchiveListResponseVo;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/own")
@RequiredArgsConstructor
@CrossOrigin(value = "*")
public class OwnController {

    private final ArchiveService archiveService;

    @GetMapping("/archives")
    public CommonResponse<ArchiveListResponseVo> getOwnArchiveList(
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "uuid") UUID uuid){

        return CommonResponse.success("내가 작성한 족보 목록을 성공적으로 불러왔습니다.",
            ArchiveListResponseDto.dtoToVo(archiveService.getOwnArchiveList(page, size, uuid)));



    }

}
