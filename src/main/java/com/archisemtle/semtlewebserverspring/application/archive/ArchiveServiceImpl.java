package com.archisemtle.semtlewebserverspring.application.archive;


import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.domain.archive.Archive;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveRequestDto;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.archive.ArchiveRepository;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.archisemtle.semtlewebserverspring.domain.archive.Archive;

@Service
@AllArgsConstructor
public class ArchiveServiceImpl implements ArchiveService{

    private final ArchiveRepository archiveRepository;

    @Override
    public void createArchiveBoard(ArchiveRequestDto requestDto) {
        Archive archive = Archive.builder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .writer(requestDto.getWriter())
            .date(new Date())
            .build();

        archiveRepository.save(archive);

    }

    @Override
    public ArchiveResponseDto readArchiveBoard(Long id) {
        Archive archive = archiveRepository.findById(id)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_DATA)
            );

        return new ArchiveResponseDto(archive);
    }

    @Override
    public void updateArchiveBoard(Long id, ArchiveRequestDto requestDto) {
        Archive prevArchive = archiveRepository.findById(id)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_DATA)
            );

        Archive updateArchive = Archive.builder()
            .id(id)
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .writer(requestDto.getWriter())
            .date(prevArchive.getDate())
            .build();

        archiveRepository.save(updateArchive);

    }

    @Override
    public void deleteArchiveBoard(Long id) {
        archiveRepository.deleteById(id);
    }
}
