package com.archisemtle.semtlewebserverspring.infrastructure.activity;

import com.archisemtle.semtlewebserverspring.domain.activity.ActivityImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityImageRepository extends JpaRepository<ActivityImage, Long> {

}
