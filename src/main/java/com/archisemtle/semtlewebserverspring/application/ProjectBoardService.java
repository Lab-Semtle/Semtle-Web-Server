package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.dto.AddProjectBoardRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectBoardPageResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectBoardResponseDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectListRequestDto;
import com.archisemtle.semtlewebserverspring.dto.UpdateProjectBoardRequestDto;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ProjectBoardService {

    void addProjectBoard(AddProjectBoardRequestDto addProjectBoardRequestDto);

    ProjectBoardResponseDto getProjectBoard(Long id);

    Page<ProjectListRequestDto> getProjectBoardList(int page, int size);

    ProjectBoardPageResponseDto getProjectBoardPage(Long id);

    void deleteProjectBoard(Long id);

    void updateProjectBoard(Long id, UpdateProjectBoardRequestDto updateProjectBoardRequestDto);
}
