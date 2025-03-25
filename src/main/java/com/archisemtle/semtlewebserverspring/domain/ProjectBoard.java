package com.archisemtle.semtlewebserverspring.domain;

import com.archisemtle.semtlewebserverspring.common.BaseTimeEntity;
import com.archisemtle.semtlewebserverspring.common.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

@Getter
@Entity(name = "project_board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_board_id")
    private Long id;

    @Column(name = "project_board_title", length = 50, nullable = false)
    private String title;

    @Column(name = "project_board_content", length = 1000)
    private String content;

    @Column(name = "project_board_writer_uuid", nullable = false)
    private String writerUuid;

    @Column(name = "project_writer_name", nullable = false)
    private String writerName;

    @Column(name = "project_writer_contact")
    @Comment("작성자 연락처")
    private String contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_type_category_id", nullable = false)
    @Comment("카테고리 ID")
    private ProjectTypeCategory projectTypeCategory;

    @Column(name = "project_start_time", nullable = false)
    @Comment("프로젝트 시작일")
    private LocalDate projectStartTime;

    @Column(name = "project_end_time", nullable = false)
    @Comment("프로젝트 종료일")
    private LocalDate projectEndTime;

    @Column(name = "end_date", nullable = false)
    @Comment("프로젝트 모집 종료일")
    private LocalDate projectRecruitingEndTime;

    @Column(name = "project_status", nullable = false)
    @Comment("프로젝트 상태")
//    @ColumnDefault("'RECRUITING'") //Todo 추후에 초기화 값이 무조건 RECRUITING이 아닐수도 있으면 서비스로직에서 값을 추가하는 걸로
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @Builder
    public ProjectBoard(Long id, String title, String content, String writerUuid, String writerName,
        String contact, ProjectTypeCategory projectTypeCategory, LocalDate projectStartTime,
        LocalDate projectEndTime, LocalDate projectRecruitingEndTime, ProjectStatus projectStatus) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writerUuid = writerUuid;
        this.writerName = writerName;
        this.contact = contact;
        this.projectTypeCategory = projectTypeCategory;
        this.projectStartTime = projectStartTime;
        this.projectEndTime = projectEndTime;
        this.projectRecruitingEndTime = projectRecruitingEndTime;
        this.projectStatus = projectStatus;
    }


}
