package com.example.bookshop.dto.chapter;


import jakarta.persistence.Lob;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterCreateRequest {
    String title;
    @Lob
    String content;
    int   chap;

}
