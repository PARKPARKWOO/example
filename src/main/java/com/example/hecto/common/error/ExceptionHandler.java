package com.example.hecto.common.error;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(BoardException.class)
    public Mono<String> boardException(BoardException e) {
        return Mono.just(e.getMessage());
    }
}
