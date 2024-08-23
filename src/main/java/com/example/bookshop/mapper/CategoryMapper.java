package com.example.bookshop.mapper;

import com.example.bookshop.dto.category.CategoryCreateRequest;
import com.example.bookshop.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryEntity requestToEntity(CategoryCreateRequest request);
}
