package com.example.bookshop.controller.web;

import com.example.bookshop.dto.ApiResponse;
import com.example.bookshop.dto.chapter.*;
import com.example.bookshop.service.IChapterService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController("UserChapterController")
@RequestMapping("/api/chapter")
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
    @PutMapping("/{chapterId}")
    public ApiResponse<ChapterUpdateResponse>  updateChapter(@PathVariable String chapterId , @RequestBody ChapterUpdateRequest request) {
        var result = chapterService.updateChapter(chapterId,request);
        return  ApiResponse.<ChapterUpdateResponse>builder()
                .result(result)
                .build();
    }



}
