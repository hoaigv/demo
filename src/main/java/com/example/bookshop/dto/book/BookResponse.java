package com.example.bookshop.dto.book;

import com.example.bookshop.dto.chapter.ChapterTitleResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
    String id;
    String title;
    String description;
    String resume;
    String reissue;
    String image;
    String publisher;
    Set<String> authors;
    Set<String> categories;
    Set<ChapterTitleResponse> chapters;

    public void setAuthor(Stream<String> stringStream) {
        stringStream.forEach(authors::add);
    }
}
