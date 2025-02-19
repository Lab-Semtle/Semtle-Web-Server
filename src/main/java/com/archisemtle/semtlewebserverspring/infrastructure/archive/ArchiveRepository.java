package com.archisemtle.semtlewebserverspring.infrastructure.archive;

import com.archisemtle.semtlewebserverspring.domain.archive.Archive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Long> {

    Page<Archive> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Archive> findByContentContainingIgnoreCase(String content, Pageable pageable);

}
