package com.archisemtle.semtlewebserverspring.domain;

import com.archisemtle.semtlewebserverspring.common.ProjectStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity(name = "project_board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectBoard {

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
    private String contact;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_type_category_id", nullable = false)
    private ProjectTypeCategory projectTypeCategory;
    @Column(name = "project_start_time", nullable = false)
    private Date projectStartTime;
    @Column(name = "project_end_time", nullable = false)
    private Date projectEndTime;
    @Column(name = "project_recruiting_end_time", nullable = false)
    private Date projectRecruitingEndTime;
    @Column(name = "project_status", nullable = false)
//    @ColumnDefault("'RECRUITING'") //Todo 추후에 초기화 값이 무조건 RECRUITING이 아닐수도 있으면 서비스로직에서 값을 추가하는 걸로
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @Builder
    public ProjectBoard(Long id, String title, String content, String writerUuid, String writerName,
        String contact, ProjectTypeCategory projectTypeCategory, Date projectStartTime,
        Date projectEndTime, Date projectRecruitingEndTime, ProjectStatus projectStatus) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.contact = contact;
        this.writerUuid = writerUuid;
        this.writerName = writerName;
        this.projectTypeCategory = projectTypeCategory;
        this.projectStartTime = projectStartTime;
        this.projectEndTime = projectEndTime;
        this.projectRecruitingEndTime = projectRecruitingEndTime;
        this.projectStatus = projectStatus;
    }
}
