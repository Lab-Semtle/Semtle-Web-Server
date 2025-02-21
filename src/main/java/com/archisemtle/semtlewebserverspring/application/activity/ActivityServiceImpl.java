package com.archisemtle.semtlewebserverspring.application.activity;

import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.domain.activity.Activity;
import com.archisemtle.semtlewebserverspring.dto.activity.ActivityRequestDto;
import com.archisemtle.semtlewebserverspring.dto.activity.ActivityResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.activity.ActivityRepository;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ActivityServiceImpl implements ActivityService{

    private static final Logger log = LoggerFactory.getLogger(ActivityServiceImpl.class);

    private final ActivityRepository activityRepository;

    @Override
    public void createActivityBoard(ActivityRequestDto requestDto) throws IOException {
        Activity activity = Activity.builder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .writer(requestDto.getWriter())
            .createdAt(new Date())
            .build();

        activityRepository.save(activity);
    }

    @Override
    public ActivityResponseDto readActivityBoard(Long id){
        Activity activity = activityRepository.findById(id).orElseThrow(
            ()-> new BaseException(BaseResponseStatus.NO_DATA)
        );

        return new ActivityResponseDto(activity);
    }

    @Transactional
    @Override
    public void updateActivityBoard(Long id, ActivityRequestDto requestDto){
        Activity activity = activityRepository.findById(id).orElseThrow(
            ()-> new BaseException(BaseResponseStatus.NO_DATA)
        );

        Activity changedActivity = Activity.builder()
                .boardId(id)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .writer(requestDto.getWriter())
                .createdAt(requestDto.getCreatedAt())
                .uuid(activity.getUuid())
                .build();



        activityRepository.save(changedActivity);
    }

    @Override
    public void deleteActivityBoard(Long id){
        activityRepository.deleteById(id);
    }
}

