package com.archisemtle.semtlewebserverspring.domain.archive;

import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveRequestDto;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveResponseDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Archive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(unique = false, nullable = false)
    private String title;
    private String writer;
    private String content;
    private Date date;

    @Builder
    public Archive(Long id, String title, String writer, String content, Date date)
    {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.date = date;
    }
}
