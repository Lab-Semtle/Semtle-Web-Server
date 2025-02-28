package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByUuid(UUID uuid);

    Optional<Member> findByEmail(String email);

    Page<Member> findAll(Pageable pageable);

    Page<Member> findByUsernameContaining(String searchName, Pageable pageable);

}