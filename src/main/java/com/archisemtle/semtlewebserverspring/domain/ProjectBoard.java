package com.archisemtle.semtlewebserverspring.domain;

import com.archisemtle.semtlewebserverspring.common.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.persistence.*;

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
public class ProjectBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_board_id")
    @Comment("게시물 ID")
    private Long id;

    @Column(name = "project_board_title", length = 50, nullable = false)
    @Comment("제목")
    private String title;

//    @Column(name = "project_board_sub_title", length = 50, nullable = false)//0210 추가
//    @Comment("소제목")
//    private String subTitle;

    @Column(name = "project_board_content", length = 1000)
    @Comment("내용")
    private String content;

    @Column(name = "project_board_writer_uuid", nullable = false)
    @Comment("작성자 ID")
    private String writerUuid;

    @Column(name = "project_writer_name", nullable = false)
    @Comment("작성자 이름")
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
    private Date projectStartTime;

    @Column(name = "project_end_time", nullable = false)
    @Comment("프로젝트 종료일")
    private Date projectEndTime;

    @Column(name = "end_date", nullable = false)
    @Comment("프로젝트 모집 종료일")
    private Date projectRecruitingEndTime;

    @Column(name = "project_status", nullable = false)
    @Comment("프로젝트 상태")
//    @ColumnDefault("'RECRUITING'") //Todo 추후에 초기화 값이 무조건 RECRUITING이 아닐수도 있으면 서비스로직에서 값을 추가하는 걸로
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @Comment("사용 유무")
    @Column(name = "USE_YN")
    private String useYn;               //0210 추가

    @Comment("프로젝트 링크")
    @Column(name = "PROJECT_LINK")
    private String projectLink;         //0210 추가

    @Comment("게시글 생성 일")
    @CreationTimestamp
    @Column(name = "CREATE_DT", updatable = false)
    private Date createDt;              //0210 추가

    @Comment("게시글 마지막 수정 일")
    @UpdateTimestamp
    @Column(name = "UPDATE_DT")
    private Date updateDt;              //0210 추가

    @Comment("참여 인력")
    @Column(name = "PROJECT_MEMBER")
    private String projectMember;              //0210 추가

    @OneToMany(mappedBy = "projectBoard", fetch = FetchType.LAZY)
    private Set<ProjectImage> images;      //0210 추가

    @OneToMany(mappedBy = "projectBoard", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private Set<RelationFieldProjectPostMiddle> relationFieldProjectPostMiddleList;      //0210 추가

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    @Builder
    public ProjectBoard(Long id, String title, String content, String writerUuid, String writerName,
                        String contact, ProjectTypeCategory projectTypeCategory, Date projectStartTime,
                        Date projectEndTime, Date projectRecruitingEndTime, ProjectStatus projectStatus, String useYn,
                        String projectLink, String projectMember) {
        this.id = id;
        this.title = title;
//        this.subTitle = subTitle;
        this.content = content;
        this.writerUuid = writerUuid;
        this.writerName = writerName;
        this.contact = contact;
        this.projectTypeCategory = projectTypeCategory;
        this.projectStartTime = projectStartTime;
        this.projectEndTime = projectEndTime;
        this.projectRecruitingEndTime = projectRecruitingEndTime;
        this.projectStatus = projectStatus;
        this.useYn = useYn;
        this.projectLink = projectLink;
        this.projectMember = projectMember;
    }


}
