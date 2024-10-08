package com.example.bookshop.service;

import com.example.bookshop.dto.book.BookCreateRequest;
import com.example.bookshop.dto.book.BookCreateResponse;
import com.example.bookshop.dto.book.BookResponse;
import com.example.bookshop.dto.book.BookSearchResponse;
import com.example.bookshop.infra.spec.BookSpecification;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBookService {
    BookResponse getBook(String id);
    BookCreateResponse createBook(BookCreateRequest request , MultipartFile file);
    List<BookResponse> getAllBooks(Pageable pageable, BookSpecification filter);
    List<BookSearchResponse> searchBooks(BookSpecification filter , Pageable pageable);

}
