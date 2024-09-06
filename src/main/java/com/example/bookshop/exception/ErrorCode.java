package com.example.bookshop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalidd key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "user already existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1003, "user not found", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1004, "username is invalid", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1005, "password is invalid", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated user", HttpStatus.UNAUTHORIZED),
    ROLE_NOT_FOUND(1010, "role not found", HttpStatus.NOT_FOUND),
    ACCESS_DENIED(1012, "You do not have permission to access any links.", HttpStatus.FORBIDDEN),
    INVALID_DOB(1011, "Your age must be at least {min} ", HttpStatus.BAD_REQUEST),
    BOOK_NOT_FOUND(1013, "Book not found", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(1014, "Category not found", HttpStatus.NOT_FOUND),
    AUTHOR_NOT_FOUND(1015, "AUTHOR_NOT_FOUND not found", HttpStatus.NOT_FOUND),
    CHAPTER_NOT_FOUND(1016, " CHAPTER_NOT_FOUND not found", HttpStatus.NOT_FOUND),
    COMMENT_NOT_FOUND(1016, " COMMENT_NOT_FOUND not found", HttpStatus.NOT_FOUND),
    FILE_NOT_FOUND(1016, " FILE not found", HttpStatus.NOT_FOUND),
    NOTIFICATION_NOT_SUCCESS(1017, " NOTIFICATION_NOT_SUCCESS", HttpStatus.NOT_FOUND)




    ;
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
