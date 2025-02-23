package com.archisemtle.semtlewebserverspring.domain.activity;

import com.archisemtle.semtlewebserverspring.dto.activity.ActivityRequestDto;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveRequestDto;
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
@Builder(toBuilder = true)
public class Activity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long boardId;

    @Column(nullable = false, unique = false)
    private String title;
    private String content;
    private String writer;

    @Column(nullable = true, unique = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(nullable = true, unique = false)
    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActivityImage> images;

    public Activity(ArchiveRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.writer = requestDto.getWriter();
        this.createdAt = requestDto.getCreatedAt();
    }
}
