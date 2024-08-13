package com.example.hecto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Setter
public class Board {
    // getter 및 setter 메서드
    private Long id;
    private String title;
    private String content;
    private String owner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 전체 필드를 포함하는 생성자
    public Board(Long id, String title, String content, String owner, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.owner = owner;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // 필드를 생성하는 static 메서드
    public static Board create(String title, String content, String owner, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Board(null, title, content, owner, createdAt, updatedAt);
    }

    // update 메서드
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }
}

