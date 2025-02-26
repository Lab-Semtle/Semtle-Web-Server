package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.common.utils.ConvertUtils;
import com.archisemtle.semtlewebserverspring.domain.ProjectPromotion;
import com.archisemtle.semtlewebserverspring.dto.OwnerResponseDto;
import com.archisemtle.semtlewebserverspring.dto.OwnertBoardResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    @Override
    public OwnerResponseDto getPromotionsByOwnerId(int page, int size, String uuid) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProjectPromotion> projectBoardPage = ownerRepository.findByOwnerId(uuid, pageable);

        // ProjectBoard -> PromotionResponseDto 변환
        List<OwnertBoardResponseDto> projectOwnerDtos = projectBoardPage.getContent().stream()
                .map(this::convertToProjectOwnerResponseDto)
                .collect(Collectors.toList());


        return new OwnerResponseDto(
                projectBoardPage.getTotalElements(),  // totalItems (총 데이터 개수)
                page,                                // currentPage (현재 페이지)
                projectBoardPage.getTotalPages(),    // totalPage (전체 페이지 개수)
                projectOwnerDtos                     // projects (현재 페이지의 데이터 목록)
        );
    }

    private OwnertBoardResponseDto convertToProjectOwnerResponseDto(ProjectPromotion projectPromotion) {
        return new OwnertBoardResponseDto(
                projectPromotion.getId(),           // board_id
                projectPromotion.getTitle(),        //title
                ConvertUtils.changeDateToString(projectPromotion.getCreateDt())     //create_date
        );
    }

}
