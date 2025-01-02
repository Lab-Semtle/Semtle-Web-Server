package com.archisemtle.semtlewebserverspring.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProjectStatus {
    RECRUITING,  // 모집중
    IN_PROGRESS, // 진행중
    CLOSED       // 마감

}
