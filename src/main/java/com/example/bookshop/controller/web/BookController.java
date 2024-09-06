package com.example.bookshop.controller.web;

import com.example.bookshop.dto.ApiResponse;
import com.example.bookshop.dto.book.BookCreateRequest;
import com.example.bookshop.dto.book.BookCreateResponse;
import com.example.bookshop.dto.book.BookResponse;
import com.example.bookshop.dto.book.BookSearchResponse;
import com.example.bookshop.infra.spec.BookSpecification;
import com.example.bookshop.service.IBookService;
import com.example.bookshop.utils.SortUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("WebBookController")
@RequestMapping("/api/books")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookController {
    IBookService bookService;
    private final static String DEFAULT_FILTER_LIMIT = "20";
    private final static String DEFAULT_FILTER_OFFSET = "0";
    private final static Sort DEFAULT_FILTER_SORT = Sort.by(Sort.Direction.DESC, "createDate");

    @GetMapping("/{bookId}")
    public ApiResponse<BookResponse> getBookById(@PathVariable String bookId) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.getBook(bookId))
                .build();
    }


    @GetMapping
    public ApiResponse<List<BookResponse>> getListBook(
            BookSpecification filter,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_LIMIT) int limit,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_OFFSET) int offset,
            @RequestParam(required = false) List<String> sortParam

    ) {
        Sort sort = DEFAULT_FILTER_SORT;
        if (sortParam == null || sortParam.isEmpty()) {
            sort = SortUtils.parseSortParams(sortParam);
        }
        Pageable pageable = PageRequest.of(offset, limit, sort);
        var result = bookService.getAllBooks(pageable, filter);

        return ApiResponse.<List<BookResponse>>builder()
                .result(result)
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<BookSearchResponse>> getListBookSearch(
            BookSpecification filter
    ) {
        Pageable pageable = PageRequest.of(0, 5, DEFAULT_FILTER_SORT);
        var result = bookService.searchBooks(filter, pageable);
        return ApiResponse.<List<BookSearchResponse>>builder().result(result).build();
    }
}
