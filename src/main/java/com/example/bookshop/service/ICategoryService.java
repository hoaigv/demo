package com.example.bookshop.service;

import com.example.bookshop.dto.category.CategoryCreateRequest;
import com.example.bookshop.dto.category.CategoryCreateResponse;
import com.example.bookshop.dto.category.CategoryResponse;

public interface ICategoryService {
   CategoryCreateResponse createCategory(CategoryCreateRequest request);
   CategoryResponse getAllCategories();
}
