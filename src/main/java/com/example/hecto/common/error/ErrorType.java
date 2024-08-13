package com.example.hecto.common.error;

import lombok.Getter;

@Getter
public enum ErrorType {
    BOARD_NOT_FOUND("board 를 찾지 못했습니다"),
    UNAUTHORIZED("작업을 수행할 권한이 없습니다.");

    private final String message;

    ErrorType(String message) {
        this.message = message;
    }
}
