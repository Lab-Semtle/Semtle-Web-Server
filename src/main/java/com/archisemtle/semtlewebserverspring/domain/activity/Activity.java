package com.archisemtle.semtlewebserverspring.domain.activity;

import com.archisemtle.semtlewebserverspring.dto.activity.ActivityRequestDto;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import jakarta.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "activity")
@AllArgsConstructor
@Builder
public class Activity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Setter(AccessLevel.NONE)
    private Long boardId;

    @Column(nullable = false, unique = false)
    private String title;
    private String content;
    private String writer;

    @Column(nullable = true, unique = false)
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActivityImage> images = new ArrayList<>();

    public Activity(ActivityRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.writer = requestDto.getWriter();
        this.createDate = requestDto.getCreateDate();
    }
}
