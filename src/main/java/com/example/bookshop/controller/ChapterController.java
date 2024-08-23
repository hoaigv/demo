package com.example.bookshop.controller;

import com.example.bookshop.dto.ApiResponse;
import com.example.bookshop.dto.chapter.ChapterCreateRequest;
import com.example.bookshop.dto.chapter.ChapterCreateResponse;
import com.example.bookshop.dto.chapter.ChapterReadResponse;
import com.example.bookshop.service.IChapterService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chapter")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChapterController {
    IChapterService chapterService;
    @PostMapping("create/{bookId}")
    public ApiResponse<ChapterCreateResponse> createChapter(@PathVariable String bookId , @RequestBody ChapterCreateRequest request) {
       var result = chapterService.createChapter(bookId,request);
        return ApiResponse.<ChapterCreateResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{chapterId}")
    public ApiResponse<ChapterReadResponse> getChapterById(@PathVariable String chapterId) {
        var resp = chapterService.getChapter(chapterId);
        return ApiResponse.<ChapterReadResponse>builder()
                .result(resp)
                .build();
    }



}
