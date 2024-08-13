package com.example.hecto.controller.request;

import com.example.hecto.service.command.DeleteBoardCommand;

public record DeleteBoardRequest(Long id) {
    public DeleteBoardCommand toCommand(String owner) {
        return new DeleteBoardCommand(id, owner);
    }
}
