package com.example.bookshop.service;

import com.example.bookshop.dto.chapter.ChapterCreateRequest;
import com.example.bookshop.dto.chapter.ChapterCreateResponse;
import com.example.bookshop.dto.chapter.ChapterReadResponse;
import com.example.bookshop.dto.chapter.ChapterTitleResponse;

import java.util.Set;

public interface IChapterService {
    Set<ChapterTitleResponse> getAllChapterTitles(String bookId);
    ChapterCreateResponse createChapter( String bookId,ChapterCreateRequest request);
    ChapterReadResponse getChapter(String chapterId);
}
