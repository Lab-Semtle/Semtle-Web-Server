package com.archisemtle.semtlewebserverspring.presentation.activity;

import com.archisemtle.semtlewebserverspring.application.activity.ActivityService;
import com.archisemtle.semtlewebserverspring.application.activity.ActivityServiceImpl;
import com.archisemtle.semtlewebserverspring.dto.activity.ActivityRequestDto;
import com.archisemtle.semtlewebserverspring.dto.activity.ActivityResponseDto;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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

@RestController
@RequestMapping("/api1/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityServiceImpl activityService;

    @PostMapping
    public ResponseEntity<ActivityResponseDto> createActivity(@RequestPart("requestDto")
        ActivityRequestDto requestDto, @RequestPart(value = "files", required = false) List<MultipartFile> files)
        throws IOException {
        ActivityResponseDto saveDto = activityService.createActivityBoard(requestDto, files);
        return ResponseEntity.ok(saveDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponseDto> getActivity(@PathVariable Long id){
        ActivityResponseDto dto = activityService.readActivityBoard(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateActivity(@PathVariable Long id, @RequestPart("requestDto") ActivityRequestDto requestDto,
                                                @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        Long updateId = activityService.updateActivityBoard(id, requestDto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivityBoard(id);
        return ResponseEntity.ok(id);
    }

}
