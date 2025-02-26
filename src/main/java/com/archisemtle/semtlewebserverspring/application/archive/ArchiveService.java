package com.archisemtle.semtlewebserverspring.application.archive;

import com.archisemtle.semtlewebserverspring.domain.archive.Archive;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveListRequestDto;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveListResponseDto;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveRequestDto;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.archive.ArchiveRepository;
import java.util.List;
import java.util.UUID;

public interface ArchiveService {


    public void createArchiveBoard(ArchiveRequestDto requestDto);
    public ArchiveResponseDto readArchiveBoard(Long id);
    public void updateArchiveBoard(Long id, ArchiveRequestDto requestDto);
    public void deleteArchiveBoard(Long id);
    public ArchiveListResponseDto getArchiveList(ArchiveListRequestDto requestDto);
    public ArchiveListResponseDto getOwnArchiveList(int page, int limit, UUID uuid);

}
