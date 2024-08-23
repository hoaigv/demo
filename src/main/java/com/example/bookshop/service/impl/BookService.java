package com.example.bookshop.service.impl;

import com.example.bookshop.dto.book.BookCreateRequest;
import com.example.bookshop.dto.book.BookCreateResponse;
import com.example.bookshop.dto.book.BookResponse;
import com.example.bookshop.entity.AuthorEntity;
import com.example.bookshop.entity.BookEntity;
import com.example.bookshop.infra.spec.BookSpecification;
import com.example.bookshop.repository.AuthorRepository;
import com.example.bookshop.entity.CategoryEntity;
import com.example.bookshop.exception.CustomRunTimeException;
import com.example.bookshop.exception.ErrorCode;
import com.example.bookshop.mapper.BookMapper;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.CategoryRepository;
import com.example.bookshop.service.IBookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookService implements IBookService {
    BookRepository bookRepository;
    CategoryRepository categoryRepository;
    AuthorRepository authorRepository;
    BookMapper bookMapper;
    ChapterService chapterService;


    @Override
    public List<BookResponse> getAllBooks(Pageable pageable, BookSpecification filter) {
        var result = bookRepository.findAll(filter, pageable);
        List<BookResponse> bookResponses = new ArrayList<>();
        for (BookEntity book : result) {
            var bookItem = bookMapper.bookToBookResponse(book);
            Set<String> authors = new HashSet<>();
            book.getAuthors().forEach(
                    authorEntity -> {
                        authors.add(authorEntity.getName());
                    }
            );
            bookItem.setAuthors(authors);
            Set<String> categories = new HashSet<>();
            book.getCategories().forEach(
                    categoryEntity -> {
                        categories.add(categoryEntity.getName());
                    }
            );
            bookItem.setCategories(categories);
            bookItem.setChapters(chapterService.getAllChapterTitles(book.getId()));
            bookResponses.add(bookItem);
        }
        return bookResponses;
    }

    @Override
    public BookResponse getBook(String id) {
        var bookEntity = bookRepository.findById(id).orElseThrow(
                () -> new CustomRunTimeException(ErrorCode.BOOK_NOT_FOUND)
        );
        var resp = bookMapper.bookToBookResponse(bookEntity);
        Set<String> authors = new HashSet<>();
        bookEntity.getAuthors().forEach(
                authorEntity -> {
                    authors.add(authorEntity.getName());
                }
        );
        resp.setAuthors(authors);
        Set<String> categories = new HashSet<>();
        bookEntity.getCategories().forEach(
                categoryEntity -> {
                    categories.add(categoryEntity.getName());
                }
        );
        resp.setCategories(categories);
        resp.setChapters(chapterService.getAllChapterTitles(id));
        return resp;
    }

    @Override
    @Transactional
    public BookCreateResponse createBook(BookCreateRequest request) {
        var bookEntity = bookMapper.booktoBookEntity(request);
        bookEntity.setChapters(null);
        Set<CategoryEntity> categories = new HashSet<>();
        for (String categoryId : request.getCategories()) {
            categories.add(categoryRepository.findById(categoryId).orElseThrow(
                    () -> new CustomRunTimeException(ErrorCode.CATEGORY_NOT_FOUND)
            ));
        }
        bookEntity.setCategories(categories);

        Set<AuthorEntity> authors = new HashSet<>();
        request.getAuthor().forEach(author -> {
            var auth = authorRepository.findById(author);
            if (auth.isPresent()) {
                authors.add(auth.get());
            } else {
                var newAuthor = authorRepository.save(AuthorEntity.builder().name(author).biography("").build());
                authors.add(newAuthor);
            }
        });
        bookEntity.setAuthors(authors);
        var result = bookRepository.save(bookEntity);
        return BookCreateResponse.builder()
                .title(result.getTitle())
                .id(result.getId())
                .build();
    }
}
