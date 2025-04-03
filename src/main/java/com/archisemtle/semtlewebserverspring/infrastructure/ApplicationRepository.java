package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.domain.Application;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Page<Application> findByApplicantId(Long applicantId, Pageable pageable);
    Page<Application> findByMemberId(int memberId, Pageable pageable);

    List<Application> findByMemberIdAndPostId(int memberId, Long postId);

    Optional<Application> findByApplicantId(Long applicantId);
}
