package com.archisemtle.semtlewebserverspring.domain.archive;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class ArchiveImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    private String imageUrl;


    @Builder
    public ArchiveImage(Long id, String imageUrl){
        this.id = id;
        this.imageUrl = imageUrl;
    }

}
