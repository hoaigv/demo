package com.example.bookshop.service;

import com.example.bookshop.dto.comment.CommentRequest;
import com.example.bookshop.dto.comment.CommentResponse;

import java.util.List;

public interface ICommentService {
    CommentResponse createComment(String bookId, CommentRequest request );
    CommentResponse updateComment(String commentId, CommentRequest request);
    List<CommentResponse> getAllComments(String bookId);
}
