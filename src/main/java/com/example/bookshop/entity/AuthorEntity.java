package com.example.bookshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class AuthorEntity  extends BaseEntity{

    String name;
    String biography;

    @ManyToMany(mappedBy = "authors")
    @JsonBackReference
    Set<BookEntity> books = new HashSet<>();

}
