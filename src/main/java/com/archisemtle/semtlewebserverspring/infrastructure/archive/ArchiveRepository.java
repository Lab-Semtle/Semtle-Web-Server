package com.archisemtle.semtlewebserverspring.infrastructure.archive;

import com.archisemtle.semtlewebserverspring.domain.archive.Archive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Long> {

    Page<Archive> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
        String titleKeyword,
        String contentKeyword,
        Pageable pageable
    );

}
