package com.example.bookshop.service.impl;

import com.example.bookshop.dto.category.CategoryCreateRequest;
import com.example.bookshop.dto.category.CategoryCreateResponse;
import com.example.bookshop.dto.category.CategoryResponse;
import com.example.bookshop.entity.CategoryEntity;
import com.example.bookshop.mapper.CategoryMapper;
import com.example.bookshop.repository.CategoryRepository;
import com.example.bookshop.service.ICategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryService implements ICategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    @Override
    @Transactional
    public CategoryCreateResponse createCategory(CategoryCreateRequest request) {
        var newCategory = categoryRepository.save(categoryMapper.requestToEntity(request));
        return CategoryCreateResponse.builder()
                .name(newCategory.getName())
                .build();
    }

    @Override
    public CategoryResponse getAllCategories() {
        var result = categoryRepository.findAll();
        var resp = result.stream().map(
                CategoryEntity::getName
        ).collect(Collectors.toSet());
        return CategoryResponse.builder()
                .categories(resp)
                .build();
    }

}
