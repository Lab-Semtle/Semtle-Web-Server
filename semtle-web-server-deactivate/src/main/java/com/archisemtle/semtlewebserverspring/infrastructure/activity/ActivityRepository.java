package com.archisemtle.semtlewebserverspring.infrastructure.activity;

import com.archisemtle.semtlewebserverspring.domain.activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
