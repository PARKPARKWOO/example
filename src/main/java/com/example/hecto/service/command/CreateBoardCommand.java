package com.example.hecto.service.command;

import java.time.LocalDateTime;

public record CreateBoardCommand(String title, String content, String owner, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
