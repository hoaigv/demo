package com.example.bookshop.exception;

import com.example.bookshop.dto.ApiResponse;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.View;

import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private final View error;

    public GlobalExceptionHandler(View error) {
        this.error = error;
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<?>> handleException() {
        ApiResponse<?> apiResponse = new ApiResponse<>();

        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = CustomRunTimeException.class)
    ResponseEntity<ApiResponse<?>> handleCusTomRuntimeException(CustomRunTimeException e) {
        log.error("Custom runtime exception: ", e);
        ErrorCode errorCode =e.getErrorCode();
        ApiResponse<?> apiResponse = new ApiResponse<>();

        apiResponse.setMessage(e.getMessage());
        apiResponse.setCode(errorCode.getCode());
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }
    @ExceptionHandler(value = TokenAlreadyInvalidatedException.class)
    ResponseEntity<SignedJWT> handleTokenAlreadyInvalidatedException(TokenAlreadyInvalidatedException e) {
        SignedJWT response = null;

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

        @ExceptionHandler(value = AuthenticationServiceException.class)
        ResponseEntity<ApiResponse<?>> handleAuthenticationServiceException(AuthenticationServiceException e) {
            ApiResponse<?> apiResponse = new ApiResponse<>();
            apiResponse.setMessage(ErrorCode.UNAUTHENTICATED.getMessage());
            apiResponse.setCode(ErrorCode.UNAUTHENTICATED.getCode());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
        }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException e) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(e.getMessage());
        apiResponse.setCode(1099);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(apiResponse);
    }



    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String enumKey = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try{
             errorCode = ErrorCode.valueOf(enumKey);
        }catch (IllegalArgumentException ignored){

        }
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(errorCode.getMessage());
        apiResponse.setCode(errorCode.getCode());
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
