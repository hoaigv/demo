package com.example.bookshop.repository;

import com.example.bookshop.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, String> {
    Optional<List<CommentEntity>> findAllByBookId(String bookId);
}
