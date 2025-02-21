package com.archisemtle.semtlewebserverspring.domain.activity;

import com.archisemtle.semtlewebserverspring.dto.activity.ActivityRequestDto;
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
    @Column(nullable = true, unique = true)
    private UUID uuid;
    @Column(nullable = true, unique = false)
    private List<String> images;

}
