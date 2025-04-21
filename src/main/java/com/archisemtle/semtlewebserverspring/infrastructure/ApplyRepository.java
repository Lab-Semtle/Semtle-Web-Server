package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.domain.Apply;
import com.archisemtle.semtlewebserverspring.domain.Member;
import com.archisemtle.semtlewebserverspring.domain.ProjectBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {
    Apply findByMemberAndProjectBoardId(Member member, Long postId);
    Page<Apply> findByProjectBoard(ProjectBoard projectBoard, Pageable pageable);
    Page<Apply> findByMember(Member member, Pageable pageable);
}
