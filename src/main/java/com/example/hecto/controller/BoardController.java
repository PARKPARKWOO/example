package com.example.hecto.controller;

import com.example.hecto.common.annotation.RateLimiter;
import com.example.hecto.controller.request.CreateBoardRequest;
import com.example.hecto.controller.request.DeleteBoardRequest;
import com.example.hecto.controller.request.UpdateBoardRequest;
import com.example.hecto.enums.OrderBy;
import com.example.hecto.model.Board;
import com.example.hecto.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/board")
    @RateLimiter(key = "owner", time = 3, quota = 1, timeUnit = TimeUnit.SECONDS)
    public Mono<Long> createBoard(
            @RequestHeader String owner,
            @RequestBody CreateBoardRequest request
    ) {
        return boardService.createBoard(request.toCommand(owner));
    }

    @GetMapping("/board/{id}")
    public Mono<Board> getBoard(@PathVariable Long id) {
        return boardService.getBoardById(id);
    }

    @GetMapping("/boards")
    public Flux<Board> getBoardList(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "orderBy", defaultValue = "TITLE_ASC") OrderBy orderBy
    ) {
        return boardService.getAllBoards(offset, limit, orderBy);
    }

    @PutMapping("/board")
    public Mono<Void> updateBoard(
            @RequestHeader String owner,
            @RequestBody UpdateBoardRequest request
    ) {
        return boardService.updateBoard(request.toCommand(owner));
    }

    @DeleteMapping("/board")
    public Mono<Void> deleteBoard(
            @RequestHeader String owner,
            @RequestBody DeleteBoardRequest request
    ) {
        return boardService.deleteBoard(request.toCommand(owner));
    }
}
