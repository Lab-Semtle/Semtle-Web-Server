package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.common.utils.ConvertUtils;
import com.archisemtle.semtlewebserverspring.domain.ProjectBoard;
import com.archisemtle.semtlewebserverspring.dto.OwnerResponseDto;
import com.archisemtle.semtlewebserverspring.dto.OwnertBoardResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectBoardResponseDto2;
import com.archisemtle.semtlewebserverspring.dto.PromotionResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.OwnerRepository;
import com.archisemtle.semtlewebserverspring.infrastructure.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    @Override
    public OwnerResponseDto getPromotionsByOwnerId(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        //ToDo userId를 토큰에서 긁어올것.
        String userID = "UUID-PLACEHOLDER";
        Page<ProjectBoard> projectBoardPage = ownerRepository.findByOwnerId(userID, pageable);

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

    private OwnertBoardResponseDto convertToProjectOwnerResponseDto(ProjectBoard projectBoard) {
        return new OwnertBoardResponseDto(
                projectBoard.getId(),           // board_id
                projectBoard.getTitle(),        //title
                ConvertUtils.changeDateToString(projectBoard.getCreateDt())     //create_date
        );
    }

}
