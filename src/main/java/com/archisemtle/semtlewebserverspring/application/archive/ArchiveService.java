package com.archisemtle.semtlewebserverspring.application.archive;

import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveRequestDto;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.archive.ArchiveRepository;

public interface ArchiveService {


    public void createArchiveBoard(ArchiveRequestDto requestDto);
    public ArchiveResponseDto readArchiveBoard(Long id);
    public void updateArchiveBoard(Long id, ArchiveRequestDto requestDto);
    public void deleteArchiveBoard(Long id);

}
