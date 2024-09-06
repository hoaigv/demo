package com.example.bookshop.mapper;

import com.example.bookshop.dto.chapter.ChapterCreateRequest;
import com.example.bookshop.dto.chapter.ChapterCreateResponse;
import com.example.bookshop.dto.chapter.ChapterReadResponse;
import com.example.bookshop.dto.chapter.ChapterUpdateRequest;
import com.example.bookshop.entity.ChapterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ChapterMapper {

    ChapterEntity requestToEntity(ChapterCreateRequest request);


    ChapterEntity oldChapterToEntity(@MappingTarget  ChapterEntity oldChapter, ChapterUpdateRequest request);

    ChapterCreateResponse entityToResponse(ChapterEntity entity);


    ChapterReadResponse entityToReadResponse(ChapterEntity entity);
}
