package com.example.bookshop.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "book")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class BookEntity extends BaseEntity{

   String publisher;
   String title;
   String description;
   String resume;
   String reissue;
   String image;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_author" , joinColumns = @JoinColumn(name = "book_id"),inverseJoinColumns = @JoinColumn(name = "author_id"))
    @JsonManagedReference
    Set<AuthorEntity> authors = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_category" , joinColumns = @JoinColumn(name = "book_id"),inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonManagedReference
    Set<CategoryEntity> categories = new HashSet<>();

    @OneToMany(mappedBy = "book")
    List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    Set<ChapterEntity> chapters = new HashSet<>();



}
