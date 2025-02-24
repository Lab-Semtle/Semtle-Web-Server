package com.archisemtle.semtlewebserverspring.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectBoardSearchCondition {

    private String ProjectTypeCategoryName;
    private String relationFieldCategoryName;
}
