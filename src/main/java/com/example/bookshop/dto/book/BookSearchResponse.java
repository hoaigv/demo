package com.example.bookshop.dto.book;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookSearchResponse {
    String id;
    String title;
    String image;
    Set<String> categories;
}
