package com.example.hecto.controller.request;

import com.example.hecto.service.command.UpdateBoardCommand;

import java.time.LocalDateTime;

public record UpdateBoardRequest(Long id, String title, String content) {
    public UpdateBoardCommand toCommand(String owner) {
        return new UpdateBoardCommand(id, title, content, owner, LocalDateTime.now());
    }
}
