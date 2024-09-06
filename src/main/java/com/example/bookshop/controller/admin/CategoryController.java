package com.example.bookshop.controller.admin;

import com.example.bookshop.dto.ApiResponse;
import com.example.bookshop.dto.category.CategoryCreateRequest;
import com.example.bookshop.dto.category.CategoryCreateResponse;
import com.example.bookshop.dto.category.CategoryResponse;
import com.example.bookshop.service.ICategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/category")
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE , makeFinal = true)
public class CategoryController {
    ICategoryService categoryService;
    @PostMapping("/create")
    public ApiResponse<CategoryCreateResponse> create(@RequestBody CategoryCreateRequest request) {
        var resp = categoryService.createCategory(request);
        return ApiResponse.<CategoryCreateResponse>builder()
                .result(resp)
                .build();
    }
    @GetMapping
    public ApiResponse<CategoryResponse> getAll(){
        var resp = categoryService.getAllCategories();
        return ApiResponse.<CategoryResponse>builder()
                .result(resp)
                .build();
    }
}
