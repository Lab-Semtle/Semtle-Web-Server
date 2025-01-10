package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.dto.AddProjectBoardRequestDto;
import com.archisemtle.semtlewebserverspring.dto.ProjectBoardResponseDto;

public interface ProjectBoardService {

    void addProjectBoard(AddProjectBoardRequestDto addProjectBoardRequestDto);

    ProjectBoardResponseDto getProjectBoard(Long id);

    void getProjectBoardList();

    void deleteProjectBoard();

    void updateProjectBoard();
}
