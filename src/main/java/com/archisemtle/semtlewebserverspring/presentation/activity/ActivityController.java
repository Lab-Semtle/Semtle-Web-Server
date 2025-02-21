package com.archisemtle.semtlewebserverspring.presentation.activity;

import com.archisemtle.semtlewebserverspring.application.activity.ActivityService;
import com.archisemtle.semtlewebserverspring.application.activity.ActivityServiceImpl;
import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.dto.activity.ActivityRequestDto;
import com.archisemtle.semtlewebserverspring.dto.activity.ActivityResponseDto;
import com.archisemtle.semtlewebserverspring.vo.activity.ActivityRequestVo;
import com.archisemtle.semtlewebserverspring.vo.activity.ActivityResponseVo;
import com.fasterxml.jackson.databind.ser.Serializers.Base;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/activity")
@RequiredArgsConstructor
public class ActivityController {

    private static final Logger log = LoggerFactory.getLogger(ActivityController.class);
    private final ActivityService activityService;

    //게시물 생성
    @PostMapping
    public CommonResponse<String> createActivity(@RequestBody ActivityRequestVo requestVo)
        throws IOException {


        activityService.createActivityBoard(ActivityRequestVo.voToDto(requestVo));
        return CommonResponse.success("게시글 작성 성공");
    }

    //게시물 읽기
    @GetMapping("/{id}")
    public CommonResponse<ActivityResponseVo> getActivity(@PathVariable Long id){
        log.info("컨트롤러 진입");
        ActivityResponseDto dto = activityService.readActivityBoard(id);
        log.info("dto발견");
        return CommonResponse.success("게시물 읽기 성공", ActivityResponseDto.dtoToVo(dto));
    }

    //게시물 수정 성공
    @PutMapping("/{id}")
    public CommonResponse<String> updateActivity(@PathVariable Long id, @RequestBody ActivityRequestVo requestVo)
        throws IOException
    {
        activityService.updateActivityBoard(id, ActivityRequestVo.voToDto(requestVo));
        return CommonResponse.success("게시글 수정 성공");
    }

    //게시물 삭제 성공
    @DeleteMapping("/{id}")
    public CommonResponse<String> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivityBoard(id);
        return CommonResponse.success("게시글 삭제 성공");
    }

}
