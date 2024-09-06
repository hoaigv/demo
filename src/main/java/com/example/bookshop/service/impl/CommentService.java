package com.example.bookshop.service.impl;

import com.example.bookshop.dto.comment.CommentRequest;
import com.example.bookshop.dto.comment.CommentResponse;
import com.example.bookshop.exception.CustomRunTimeException;
import com.example.bookshop.exception.ErrorCode;
import com.example.bookshop.mapper.CommentMapper;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.CommentRepository;
import com.example.bookshop.repository.UserRepository;
import com.example.bookshop.service.ICommentService;
import com.example.bookshop.utils.AuthUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CommentService implements ICommentService {
    UserRepository userRepository;
    CommentRepository commentRepository;
    BookRepository bookRepository;
    CommentMapper commentMapper;

    @Override
    public CommentResponse createComment(String bookId, CommentRequest request) {
        var comment = commentMapper.toCommentEntity(request);
        var user = userRepository.findByUsername(AuthUtils.getUserCurrent())
                .orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomRunTimeException(ErrorCode.CHAPTER_NOT_FOUND));
        comment.setUser(user);
        comment.setBook(book);
        commentRepository.save(comment);
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .rate(comment.getRate())
                .build();
    }

    @Override
    public CommentResponse updateComment(String commentId, CommentRequest request) {
        var oldComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomRunTimeException(ErrorCode.COMMENT_NOT_FOUND));
        oldComment.setContent(request.getContent());
        oldComment.setRate(request.getRate());
        var newComment = commentRepository.save(oldComment);
        return CommentResponse.builder()
                .id(newComment.getId())
                .content(newComment.getContent())
                .rate(newComment.getRate())
                .updateDate(newComment.getUpdateDate())
                .build();

    }

    @Override
    public List<CommentResponse> getAllComments(String bookId) {
        var comments = commentRepository.findAllByBookId(bookId).orElseThrow(
                () -> new CustomRunTimeException(ErrorCode.CHAPTER_NOT_FOUND)
        );
        return comments.stream().map(comment -> CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .rate(comment.getRate())
                .updateDate(comment.getUpdateDate())
                .build()

        ).toList();
    }
}
