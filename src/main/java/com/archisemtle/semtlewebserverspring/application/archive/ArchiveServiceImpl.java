package com.archisemtle.semtlewebserverspring.application.archive;


import com.archisemtle.semtlewebserverspring.common.BaseException;
import com.archisemtle.semtlewebserverspring.common.BaseResponseStatus;
import com.archisemtle.semtlewebserverspring.domain.archive.Archive;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveListRequestDto;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveListResponseDto;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveRequestDto;
import com.archisemtle.semtlewebserverspring.dto.archive.ArchiveResponseDto;
import com.archisemtle.semtlewebserverspring.infrastructure.archive.ArchiveRepository;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.archisemtle.semtlewebserverspring.domain.archive.Archive;

@Service
@AllArgsConstructor
public class ArchiveServiceImpl implements ArchiveService{


    private final ArchiveRepository archiveRepository;

    //게시판 생성
    @Override
    public void createArchiveBoard(ArchiveRequestDto requestDto) {
        Archive archive = Archive.builder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .writer(requestDto.getWriter())
            .createdAt(LocalDateTime.now())
            .uuid(requestDto.getUuid())
            .imageUrl(requestDto.getImageUrl())
            .fileUrl(requestDto.getFileUrl())
            .build();
        archiveRepository.save(archive);

    }

    //게시판 읽기
    @Override
    public ArchiveResponseDto readArchiveBoard(Long id) {
        Archive archive = archiveRepository.findById(id)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_DATA)
            );

        return new ArchiveResponseDto(archive);
    }

    //게시판 수정
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
            .createdAt(prevArchive.getCreatedAt())
            .uuid(prevArchive.getUuid())
            .fileUrl(requestDto.getFileUrl())
            .imageUrl(requestDto.getImageUrl())
            .build();

        archiveRepository.save(updateArchive);

    }

    //게시판 삭제
    @Override
    public void deleteArchiveBoard(Long id) {
        archiveRepository.deleteById(id);
    }

    //게시물 목록 흭득
    @Override
    public ArchiveListResponseDto getArchiveList(ArchiveListRequestDto requestDto){
        Page<Archive> archivePage;
        //게시물 총 갯수를 기준이 되는 게시물 갯수로 나눈 뒤 올림하는 과정

        Pageable pageable = PageRequest.of(requestDto.getPage()-1, requestDto.getSize(),
                Sort.by(Direction.DESC, "createdAt"));

        if(requestDto.getSearch_keyword() == null && requestDto.getSearch_keyword().isEmpty())
            archivePage = archiveRepository.findAll(pageable);
        else
            archivePage = archiveRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(requestDto.getSearch_keyword(),requestDto.getSearch_keyword(),pageable);

        int total_posts = (int)archivePage.getTotalElements();
        int total_pages = (int) Math.ceil((double) total_posts / requestDto.getSize());

        ArchiveListResponseDto responseDto = ArchiveListResponseDto.builder()
            .total_post(total_posts)
            .current_page(requestDto.getPage())
            .total_pages(total_pages)
            .posts(archivePage.getContent())
            .build();

        return responseDto;
    }

    @Override
    public ArchiveListResponseDto getOwnArchiveList(int page, int limit, UUID uuid){
        Page<Archive> archivePage;

        Pageable pageable = PageRequest.of(page-1, limit,
            Sort.by(Direction.DESC, "createdAt"));

        archivePage = archiveRepository.findByUuid(uuid, pageable);
        int total_posts = (int)archivePage.getTotalElements();
        int total_pages = (int) Math.ceil((double) total_posts / limit);

        ArchiveListResponseDto responseDto = ArchiveListResponseDto.builder()
            .total_post(total_posts)
            .current_page(page)
            .total_pages(total_pages)
            .posts(archivePage.getContent())
            .build();

        return responseDto;

    }
}
