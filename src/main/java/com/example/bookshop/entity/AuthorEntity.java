package com.example.bookshop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "author")
@Table
public class AuthorEntity {
    @Id
    String name;

    String biography;

    @ManyToMany(mappedBy = "authors")
    Set<BookEntity> books = new HashSet<>();



}
