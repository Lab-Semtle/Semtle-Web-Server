package com.archisemtle.semtlewebserverspring.infrastructure.archive;

import com.archisemtle.semtlewebserverspring.domain.archive.Archive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {

}
