package com.archisemtle.semtlewebserverspring.domain.archive;


import com.archisemtle.semtlewebserverspring.vo.archive.ArchiveResponseVo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "archive")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Archive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(unique = false, nullable = false)
    private String title;
    @Column(unique = false, nullable = false)
    private String writer;
    @Column(unique = false, nullable = false)
    private String content;
    @Column(unique = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(unique = false, nullable = false)
    private UUID uuid;

    @Column(unique = false, nullable = true)
    private List<String> imageUrl;
    @Column(unique = false, nullable = true)
    private List<String> fileUrl;

    public static ArchiveResponseVo entityToVo(Archive archive){
        return ArchiveResponseVo.builder()
            .writer(archive.getWriter())
            .content(archive.getContent())
            .title(archive.getTitle())
            .createdAt(archive.getCreatedAt())
            .board_id(archive.getId())
            .imageUrl(archive.getImageUrl())
            .fileUrl(archive.getFileUrl())
            .build();
    }
}
