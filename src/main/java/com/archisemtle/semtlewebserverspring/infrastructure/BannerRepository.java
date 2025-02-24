package com.archisemtle.semtlewebserverspring.infrastructure;


import com.archisemtle.semtlewebserverspring.domain.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Integer> {

}
