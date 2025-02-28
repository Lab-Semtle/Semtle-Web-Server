package com.archisemtle.semtlewebserverspring.application.activity;

import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.domain.activity.Activity;
import com.archisemtle.semtlewebserverspring.dto.activity.ActivityListRequestDto;
import com.archisemtle.semtlewebserverspring.dto.activity.ActivityListResponseDto;
import com.archisemtle.semtlewebserverspring.dto.activity.ActivityRequestDto;
import com.archisemtle.semtlewebserverspring.dto.activity.ActivityResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.activity.ActivityRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import java.io.IOException;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
            .images(requestDto.getImages())
            .uuid(requestDto.getUuid())
            .createdAt(new Date())
            .type(requestDto.getType())
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
                .images(requestDto.getImages())
                .type(requestDto.getType())
                .build();

        activityRepository.save(changedActivity);
    }

    @Override
    public void deleteActivityBoard(Long id){
        activityRepository.deleteById(id);
    }

    @Override
    public ActivityListResponseDto readActivityListBoard(ActivityListRequestDto requestDto){
        Page<Activity> activityPage;

        Pageable pageable = PageRequest.of(requestDto.getPage()-1, requestDto.getSize(),
            Sort.by(Direction.ASC, "createdAt"));


        activityPage = activityRepository.findByTypeContainingIgnoreCase(requestDto.getType(), pageable);

        int total_posts = (int)activityPage.getTotalElements();
        int total_pages = (int) Math.ceil((double) total_posts / requestDto.getSize());

        ActivityListResponseDto responseDto = ActivityListResponseDto.builder()
            .total_post(total_posts)
            .current_page(requestDto.getPage())
            .total_pages(total_pages)
            .posts(activityPage.getContent())
            .build();

        return responseDto;
    }

    @Override
    public ActivityListResponseDto readRecentActivityListBoard(int limit){
        Pageable pageable = PageRequest.of(0,limit, Sort.by(Direction.DESC, "boardId"));

        Page<Activity> page = activityRepository.findAll(pageable);

        ActivityListResponseDto responseDto = ActivityListResponseDto.builder()
            .total_post(page.getContent().size())
            .current_page(1)
            .total_pages(1)
            .posts(page.getContent())
            .build();

        return responseDto;
    }
}

