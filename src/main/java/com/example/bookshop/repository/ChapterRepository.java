package com.example.bookshop.repository;

import com.example.bookshop.entity.ChapterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ChapterRepository extends JpaRepository<ChapterEntity,String> {
    Set<ChapterEntity> findByBookId(String bookId);
}
