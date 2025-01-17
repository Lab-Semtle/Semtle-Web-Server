package com.archisemtle.semtlewebserverspring.application.activity;

import com.archisemtle.semtlewebserverspring.domain.activity.Activity;
import com.archisemtle.semtlewebserverspring.domain.activity.ActivityImage;
import com.archisemtle.semtlewebserverspring.dto.activity.ActivityRequestDto;
import com.archisemtle.semtlewebserverspring.dto.activity.ActivityResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.activity.ActivityImageRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.activity.ActivityRepository;
import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class ActivityServiceImpl implements ActivityService{

    private final ActivityRepository activityRepository;
    private final ActivityImageRepository activityImageRepository;

    public ActivityResponseDto createActivityBoard(ActivityRequestDto requestDto,
                                                List<MultipartFile> imageFile) throws IOException {
        Activity activity = new Activity(requestDto);

        activity.builder()
            .createDate(new Date())
            .build();

        activityRepository.save(activity);

        if (imageFile != null) {
            for (MultipartFile file : imageFile) {
                if (!file.isEmpty()) {

                    String imageUrl = saveImageFile(file);
                    ActivityImage activityImage = ActivityImage.builder()
                        .imageUrl(imageUrl)
                        .activity(activity)
                        .build();

                    activity.getImages().add(activityImage);
                    activityImageRepository.save(activityImage);

                }
            }
        }
        return new ActivityResponseDto(activity);
    }

    public ActivityResponseDto readActivityBoard(Long id){
        Activity activity = activityRepository.findById(id).orElseThrow(
            ()-> new IllegalArgumentException("조회 실패")
        );
        return new ActivityResponseDto(activity);
    }

    @Transactional
    public Long updateActivityBoard(Long id, ActivityRequestDto requestDto){
        Activity activity = activityRepository.findById(id).orElseThrow(
            ()-> new IllegalArgumentException("조회 실패")
        );
        activity.builder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .writer(requestDto.getWriter())
            .createDate(new Date())
            .build();

        return activity.getBoardId();
    }
    public Long deleteActivityBoard(Long id){
        activityRepository.deleteById(id);
        return id;
    }

    private String saveImageFile(MultipartFile file) throws IOException{
        String uploadDir = "resources";

        File dir = new File(uploadDir);
        if(!dir.exists()){
            dir.mkdir();
        }

        String originalFilename = file.getOriginalFilename();
        if(originalFilename == null || originalFilename.isEmpty()){
            throw new IOException("file name error");
        }

        String uuid = UUID.randomUUID().toString();
        String saveFilename = uuid + "_" + originalFilename;

        File destFile = new File(dir, saveFilename);

        file.transferTo(destFile);

        return saveFilename;
    }
}

