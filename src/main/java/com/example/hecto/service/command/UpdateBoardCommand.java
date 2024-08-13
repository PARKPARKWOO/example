package com.example.hecto.service.command;

import java.time.LocalDateTime;

public record UpdateBoardCommand(Long id, String title, String content, String owner, LocalDateTime updatedAt) {
}
