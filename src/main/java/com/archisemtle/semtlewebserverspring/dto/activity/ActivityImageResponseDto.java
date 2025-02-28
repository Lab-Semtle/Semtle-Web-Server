package com.archisemtle.semtlewebserverspring.dto.activity;

import com.archisemtle.semtlewebserverspring.domain.activity.ActivityImage;

public class ActivityImageResponseDto {

    private Long imageId;
    private Long boardId;
    private String imageUrl;

    public ActivityImageResponseDto(ActivityImage activityImage){
        this.imageId = activityImage.getImageId();
        this.boardId = activityImage.getActivity().getBoardId();
        this.imageUrl = activityImage.getImageUrl();

    }

}
