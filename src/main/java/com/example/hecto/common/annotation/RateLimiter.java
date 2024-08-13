package com.example.hecto.common.annotation;

import lombok.Getter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

// API 호출을 제한
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RUNTIME)
public @interface RateLimiter {
    String key() default "";

    // 제한 시간
    int time() default 100;

    // 허용 개수
    int quota() default 1;

    // 시간 단위
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}