package com.archisemtle.semtlewebserverspring.domain.activity;

import com.archisemtle.semtlewebserverspring.dto.activity.ActivityRequestDto;
import com.archisemtle.semtlewebserverspring.vo.activity.ActivityResponseVo;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.*;
import jakarta.persistence.*;
import java.util.Date;

@Getter
@Entity(name = "activity")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Long boardId;

    @Column(nullable = false, unique = false)
    private String title;
    @Column(nullable = false, unique = false)
    private String content;
    @Column(nullable = false, unique = false)
    private String writer;
    @Column(nullable = true, unique = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Column(nullable = true, unique = false, columnDefinition = "uuid")
    private UUID uuid;
    @ElementCollection
    @CollectionTable(name = "activity_image", joinColumns = @JoinColumn(name = "activity_id"))
    @Column(name = "image", nullable = true, unique = false)
    private List<String> images;

    @Column(nullable = false, unique = false)
    private String type;

    public static ActivityResponseVo entityToVo(Activity activity){
        return ActivityResponseVo.builder()
            .board_id(activity.getBoardId())
            .title(activity.getTitle())
            .type(activity.getType())
            .content(activity.getContent())
            .images(activity.getImages())
            .writer(activity.getWriter())
            .createdAt(activity.getCreatedAt())
            .build();
    }

}
