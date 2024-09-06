package com.example.bookshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chapter")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterEntity extends BaseEntity {
    String title;
    Integer chap;
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_book")
    @JsonBackReference
    BookEntity book;

    @ManyToMany(mappedBy = "chapters")
    @JsonBackReference
    Set<UserEntity> users = new HashSet<>();


}
