package com.archisemtle.semtlewebserverspring.presentation.activity;

import com.archisemtle.semtlewebserverspring.application.activity.ActivityService;
import com.archisemtle.semtlewebserverspring.application.activity.ActivityServiceImpl;
import com.archisemtle.semtlewebserverspring.common.CommonResponse;
import com.archisemtle.semtlewebserverspring.dto.activity.ActivityRequestDto;
import com.archisemtle.semtlewebserverspring.dto.activity.ActivityResponseDto;
import jakarta.validation.Valid;
import java.io.IOException;
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
@RequestMapping("/api1/activity")
@RequiredArgsConstructor
public class ActivityController {

    private static final Logger log = LoggerFactory.getLogger(ActivityController.class);
    private final ActivityService activityService;

    @PostMapping(/*consumes = "multipart/form-data",*/ produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse<String> createActivity(
        @RequestBody @Valid ActivityRequestDto requestDto//,
        /*@RequestPart(value = "files", required = false) List<MultipartFile> files*/)
        throws IOException {
//        if(files == null || files.isEmpty()){
//            log.info("no file");
//        }
        ActivityResponseDto saveDto = activityService.createActivityBoard(requestDto, null);
        return CommonResponse.success("게시글 작성 성공");
    }

    @GetMapping("/{id}")
    public CommonResponse<ActivityResponseDto> getActivity(@PathVariable Long id){
        ActivityResponseDto dto = activityService.readActivityBoard(id);
        return CommonResponse.success("ok", dto);
    }

    @PutMapping("/{id}")
    public CommonResponse<String> updateActivity(@PathVariable Long id, @RequestPart("requestDto") ActivityRequestDto requestDto,
                                                @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        Long updateId = activityService.updateActivityBoard(id, requestDto);
        return CommonResponse.success("게시글 수정 성공");
    }

    @DeleteMapping("/{id}")
    public CommonResponse<String> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivityBoard(id);
        return CommonResponse.success("게시글 삭제 성공");
    }

}
