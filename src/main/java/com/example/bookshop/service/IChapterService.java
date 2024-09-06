package com.example.bookshop.service;

import com.example.bookshop.dto.chapter.*;

import java.util.List;

public interface IChapterService {
    List<ChapterTitleResponse> getAllChapterTitles(String bookId);
    ChapterCreateResponse createChapter( String bookId,ChapterCreateRequest request);
    ChapterReadResponse getChapter(String chapterId);
    ChapterUpdateResponse updateChapter(String chapterId, ChapterUpdateRequest request);

}
