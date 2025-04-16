package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.domain.ProjectJoinUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectJoinUrlRepository extends JpaRepository<ProjectJoinUrl, Long> {

}
