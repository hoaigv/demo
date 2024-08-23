package com.example.bookshop.repository;

import com.example.bookshop.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String> , JpaSpecificationExecutor<BookEntity> {
}
