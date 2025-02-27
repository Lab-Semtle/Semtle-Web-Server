package com.archisemtle.semtlewebserverspring.infrastructure;

import static com.archisemtle.semtlewebserverspring.domain.QProjectBoard.projectBoard;
import static com.archisemtle.semtlewebserverspring.domain.QProjectTypeCategory.projectTypeCategory;
import static com.archisemtle.semtlewebserverspring.domain.QRelationFieldCategory.relationFieldCategory;
import static com.archisemtle.semtlewebserverspring.domain.QRelationFieldProjectPostMiddle.relationFieldProjectPostMiddle;
import static org.springframework.util.StringUtils.hasText;

import com.archisemtle.semtlewebserverspring.common.ProjectBoardSearchCondition;
import com.archisemtle.semtlewebserverspring.common.ProjectStatus;
import com.archisemtle.semtlewebserverspring.dto.ProjectBoardListDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
@Slf4j
public class ProjectBoardRepositoryImpl implements ProjectBoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ProjectBoardListDto> searchPageComplex(ProjectBoardSearchCondition condition,
        Pageable pageable) {

        BooleanExpression projectTypeCondition = projectTypeCategoryNameEq(
            condition.getProjectTypeCategoryName());

        List<Long> matchingProjectIds = queryFactory
            .select(relationFieldProjectPostMiddle.projectBoard.id)
            .from(relationFieldProjectPostMiddle)
            .leftJoin(relationFieldCategory)
            .on(relationFieldProjectPostMiddle.relationFieldCategory.id.eq(relationFieldCategory.id))
            .where(relationFieldCategoryNameEq(condition.getRelationFieldCategoryName()))
            .fetch();

        BooleanExpression relationFieldCondition = null;
        if (hasText(condition.getRelationFieldCategoryName())) {
            relationFieldCondition = projectBoard.id.in(matchingProjectIds); // ✅ 조회된 ID 목록을 조건으로 사용
        }

        BooleanExpression filterCondition =
            projectTypeCondition != null && relationFieldCondition != null
                ? projectTypeCondition.and(relationFieldCondition)
                : (projectTypeCondition != null ? projectTypeCondition : relationFieldCondition);

        List<Tuple> result = queryFactory
            .select(
                projectBoard.id,
                projectBoard.title,
                projectBoard.writerName,
                projectTypeCategory.name,
                relationFieldCategory.id,
                relationFieldCategory.name,
                projectBoard.projectRecruitingEndTime
            )
            .from(projectBoard)
            .leftJoin(projectTypeCategory)
            .on(projectBoard.projectTypeCategory.id.eq(projectTypeCategory.id))
            .leftJoin(relationFieldProjectPostMiddle)
            .on(projectBoard.id.eq(relationFieldProjectPostMiddle.projectBoard.id))
            .leftJoin(relationFieldCategory)
            .on(relationFieldProjectPostMiddle.relationFieldCategory.id.eq(
                relationFieldCategory.id))
            .where(filterCondition)
            .orderBy(
                new CaseBuilder()
                    .when(projectBoard.projectStatus.eq(ProjectStatus.RECRUITING)).then(1)
                    .when(projectBoard.projectStatus.eq(ProjectStatus.IN_PROGRESS)).then(2)
                    .when(projectBoard.projectStatus.eq(ProjectStatus.CLOSED)).then(3)
                    .otherwise(4)
                    .asc()
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        log.info("Query Result (Tuple List): {}", result); // ✅ 쿼리 결과 로그 출력

        // DTO 변환 (List<String>으로 relationFieldCategoryName 합치기)
        Map<Long, List<Tuple>> groupedByProjectId = result.stream()
            .collect(Collectors.groupingBy(tuple -> tuple.get(projectBoard.id)));

        List<ProjectBoardListDto> dtoList = groupedByProjectId.values().stream()
            .filter(grouped -> { // ✅ relation_type 필터링 추가 (게시물 유지 여부 결정)
                String filterRelationType = condition.getRelationFieldCategoryName();
                if (!hasText(filterRelationType)) {
                    return true; // relation_type이 없으면 모든 게시물 유지
                }
                return grouped.stream()
                    .map(t -> t.get(relationFieldCategory.name))
                    .filter(Objects::nonNull)
                    .anyMatch(
                        name -> name.equals(filterRelationType)); // ✅ relation_type이 포함된 게시물만 유지
            })
            .map(grouped -> {
                Tuple first = grouped.get(0);

                List<String> relationNames = grouped.stream()
                    .map(t -> t.get(relationFieldCategory.name))
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList()); // ✅ relation_type 리스트는 모든 값 유지

                ProjectBoardListDto dto = new ProjectBoardListDto(
                    first.get(projectBoard.id),
                    first.get(projectBoard.title),
                    first.get(projectBoard.writerName),
                    first.get(projectTypeCategory.name),
                    relationNames,
                    first.get(projectBoard.projectRecruitingEndTime)
                );

                log.info("Converted DTO: {}", dto.getRelationFieldCategoryName());
                return dto;
            })
            .toList();

        // 페이징 적용 (총 개수 조회)
        JPQLQuery<Long> countQuery = queryFactory
            .select(projectBoard.count())
            .from(projectBoard)
            .leftJoin(projectTypeCategory)
            .on(projectBoard.projectTypeCategory.id.eq(projectTypeCategory.id))
            .leftJoin(relationFieldProjectPostMiddle)
            .on(projectBoard.id.eq(relationFieldProjectPostMiddle.projectBoard.id))
            .leftJoin(relationFieldCategory)
            .on(relationFieldProjectPostMiddle.relationFieldCategory.id.eq(
                relationFieldCategory.id))
            .where(
                projectTypeCategoryNameEq(condition.getProjectTypeCategoryName()),
                relationFieldCategoryNameEq(condition.getRelationFieldCategoryName())
            );

        Page<ProjectBoardListDto> pageResult = PageableExecutionUtils.getPage(dtoList, pageable,
            countQuery::fetchOne);
        log.info("Final Page Result: {}", pageResult); // ✅ 최종 반환값 로그 출력

        return pageResult;
    }

    private BooleanExpression projectTypeCategoryNameEq(String projectTypeCategoryName) {
        return hasText(projectTypeCategoryName) ? projectBoard.projectTypeCategory.name.eq(
            projectTypeCategoryName) : null;
    }

    private BooleanExpression relationFieldCategoryNameEq(String relationFieldCategoryName) {
        return hasText(relationFieldCategoryName) ?
            relationFieldCategory.name.containsIgnoreCase(relationFieldCategoryName) : null;
    }
}
