package com.example.bookshop.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TokenAlreadyInvalidatedException extends RuntimeException{
    private ErrorCode errorCode;
    public TokenAlreadyInvalidatedException(ErrorCode errorCode) {
            super(errorCode.getMessage());
            this.errorCode = errorCode;
    }

}
