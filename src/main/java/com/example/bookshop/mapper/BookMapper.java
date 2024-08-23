package com.example.bookshop.mapper;

import com.example.bookshop.dto.book.BookCreateRequest;
import com.example.bookshop.dto.book.BookResponse;
import com.example.bookshop.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
   @Mapping(target = "categories" ,ignore = true)
   @Mapping(target = "authors" ,ignore = true)
   @Mapping(target = "chapters", ignore = true)
   BookResponse bookToBookResponse(BookEntity book);
   @Mapping(target = "categories" , ignore = true)
   @Mapping(target = "authors" , ignore = true)
   @Mapping(target = "chapters" , ignore = true)
   BookEntity booktoBookEntity(BookCreateRequest bookCreateRequest);


}
