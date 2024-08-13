package com.example.hecto.common.error;

public class BoardException extends RuntimeException{
    public BoardException(ErrorType errorType) {
        super(errorType.getMessage());
    }
}
