package com.archisemtle.semtlewebserverspring.application;

import com.archisemtle.semtlewebserverspring.infrastructure.RelationFieldCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RelationFieldCategoryServiceImpl implements RelationFieldCategoryService {

    private RelationFieldCategoryRepository relationFieldCategoryRepository;

    @Override
    public void getRelationFieldCategory() {
        relationFieldCategoryRepository.findAll();
    }

    @Override
    public void addRelationFieldCategory() {

    }
}
