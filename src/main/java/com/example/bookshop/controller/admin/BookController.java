package com.example.bookshop.controller.admin;

import com.example.bookshop.dto.ApiResponse;
import com.example.bookshop.dto.book.BookCreateRequest;
import com.example.bookshop.dto.book.BookCreateResponse;
import com.example.bookshop.service.IBookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController("AdminBookController")
@RequestMapping("/api/admin/books")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookController {
    IBookService bookService;
    private final static String DEFAULT_FILTER_LIMIT = "20";
    private final static String DEFAULT_FILTER_OFFSET = "0";
    private final static Sort DEFAULT_FILTER_SORT = Sort.by(Sort.Direction.DESC, "createDate");


    @PostMapping
    public ApiResponse<BookCreateResponse> createBook(
            @RequestPart BookCreateRequest request,
            @RequestPart MultipartFile file
    ) {
        var resp = bookService.createBook(request,file);
        return ApiResponse.<BookCreateResponse>builder()
                .result(resp)
                .build();
    }





}
