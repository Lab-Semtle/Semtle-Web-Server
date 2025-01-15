package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.dto.AddProjectBoardRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectBoardResponseDto;
import com.archisemtle.semtlewebserverspring.dto.UpdateProjectBoardRequestDto;

public interface ProjectBoardService {

    void addProjectBoard(AddProjectBoardRequestDto addProjectBoardRequestDto);

    ProjectBoardResponseDto getProjectBoard(Long id);

    void getProjectBoardList();

    void deleteProjectBoard(Long id);

    void updateProjectBoard(Long id, UpdateProjectBoardRequestDto updateProjectBoardRequestDto);
}
