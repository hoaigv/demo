package com.example.bookshop.mapper;

import com.example.bookshop.dto.comment.CommentRequest;
import com.example.bookshop.dto.comment.CommentResponse;
import com.example.bookshop.entity.CommentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentEntity toCommentEntity(CommentRequest request);
    CommentResponse toCommentResponse(CommentEntity entity);
}
