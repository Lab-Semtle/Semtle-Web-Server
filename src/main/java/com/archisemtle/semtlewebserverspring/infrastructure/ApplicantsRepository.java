package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.domain.Applicants;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantsRepository extends JpaRepository<Applicants, Integer> {
    @Query(value = "SELECT pa.* FROM applicants pa " +
        "JOIN applications app ON pa.applicant_id = app.applicant_id " + // 조인 조건 수정
        "WHERE app.board_id = :boardId", nativeQuery = true) // nativeQuery를 사용하여 SQL 쿼리
    Page<Applicants> findAllWithApplication(@Param("boardId") Integer boardId, Pageable pageable);

    Optional<Applicants> findByBoardIdAndApplicantId(Integer boardId, Integer applicantId);

}

