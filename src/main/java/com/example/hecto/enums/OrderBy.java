package com.example.hecto.enums;

import lombok.Getter;

@Getter
public enum OrderBy {
    TITLE_ASC("title", "asc"),
    TITLE_DESC("title", "desc"),
    DATE_ASC("created_at", "asc"),
    DATE_DESC("created_at", "desc");

    private final String column;
    private final String direction;

    OrderBy(String column, String direction) {
        this.column = column;
        this.direction = direction;
    }
}