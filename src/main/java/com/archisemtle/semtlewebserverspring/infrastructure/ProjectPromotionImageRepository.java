package com.archisemtle.semtlewebserverspring.infrastructure;

import com.archisemtle.semtlewebserverspring.domain.ProjectPromotion;
import com.archisemtle.semtlewebserverspring.domain.ProjectPromotionImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectPromotionImageRepository extends JpaRepository<ProjectPromotionImage, Long> {

    ProjectPromotionImage findOneByProjectPromotion(ProjectPromotion projectPromotion);
}
