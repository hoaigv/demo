package com.example.bookshop.controller.web;

import com.example.bookshop.dto.ApiResponse;
import com.example.bookshop.dto.comment.CommentRequest;
import com.example.bookshop.dto.comment.CommentResponse;
import com.example.bookshop.service.ICommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("UserCommentController")
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CommentController {
     ICommentService commentService;
     @GetMapping("/{bookId}")
     public ApiResponse<List<CommentResponse>> getComment(@PathVariable("bookId") String bookId) {
          var resp =  commentService.getAllComments(bookId);
          return ApiResponse.<List<CommentResponse>>builder()
                  .result(resp)
                  .build();
     }
     @PostMapping("/{bookId}")
     public ApiResponse<CommentResponse> createComment(@PathVariable("bookId") String bookId, @RequestBody CommentRequest request) {
          var resp = commentService.createComment(bookId, request);
          return ApiResponse.<CommentResponse>builder().result(resp).build();
     }
     @PutMapping("/{commentId}")
     public ApiResponse<CommentResponse> updateComment(@PathVariable("commentId") String commentId, @RequestBody CommentRequest request) {
          var resp = commentService.updateComment(commentId, request);
          return ApiResponse.<CommentResponse>builder().result(resp).build();
     }

}
