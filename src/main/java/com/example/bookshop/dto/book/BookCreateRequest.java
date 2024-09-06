package com.example.bookshop.dto.book;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCreateRequest {
    String title;
    String publisher;
    String description;
    Set<String> author;
    Set<String> categories;
}
