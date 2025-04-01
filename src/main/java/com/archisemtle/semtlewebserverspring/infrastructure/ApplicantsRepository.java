package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.domain.Applicant;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantsRepository extends JpaRepository<Applicant, Long> {
    @Query(value = "SELECT pa.* FROM applicants pa " +
        "JOIN applications app ON pa.applicant_id = app.applicant_id " +
        "WHERE app.post_id = :postId", nativeQuery = true)
    Page<Applicant> findAllWithApplication(@Param("postId") Long postId, Pageable pageable);

    Optional<Applicant> findByPostIdAndApplicantId(Long postId, Long applicantId);
}

