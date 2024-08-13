package com.example.hecto.controller.request;

import com.example.hecto.service.command.CreateBoardCommand;

import java.time.LocalDateTime;

public record CreateBoardRequest(String title, String content) {
    public CreateBoardCommand toCommand(String owner) {
        return new CreateBoardCommand(title, content, owner, LocalDateTime.now(), LocalDateTime.now());
    }
}
