package com.example.test.infrastructure;

import com.example.test.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByUuid(UUID uuid);
}