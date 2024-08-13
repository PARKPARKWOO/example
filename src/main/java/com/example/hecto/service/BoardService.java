package com.example.hecto.service;

import com.example.hecto.common.annotation.RateLimiter;
import com.example.hecto.common.config.RateLimiterConfig;
import com.example.hecto.common.error.BoardException;
import com.example.hecto.common.error.ErrorType;
import com.example.hecto.enums.OrderBy;
import com.example.hecto.mapper.BoardMapper;
import com.example.hecto.model.Board;
import com.example.hecto.service.command.CreateBoardCommand;
import com.example.hecto.service.command.DeleteBoardCommand;
import com.example.hecto.service.command.UpdateBoardCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;

    @Transactional(readOnly = true)
    public Flux<Board> getAllBoards(int offset, int limit, OrderBy orderBy) {
        String sortBy;
        String sortOrder;

        switch (orderBy) {
            case TITLE_ASC -> {
                sortBy = "title";
                sortOrder = "asc";
            }
            case TITLE_DESC -> {
                sortBy = "title";
                sortOrder = "desc";
            }
            case DATE_ASC -> {
                sortBy = "created_at";
                sortOrder = "asc";
            }
            case DATE_DESC -> {
                sortBy = "created_at";
                sortOrder = "desc";
            }
            default -> throw new IllegalArgumentException("Invalid sort option");
        }

        return Flux.fromIterable(boardMapper.getAllBoards(offset, limit, sortBy, sortOrder));
    }

    @Transactional(readOnly = true)
    public Mono<Board> getBoardById(Long id) {
        return Mono.justOrEmpty(boardMapper.getBoardById(id));
    }

    @Transactional
    public Mono<Long> createBoard(CreateBoardCommand command) {
        Board board = Board.create(command.title(), command.content(), command.owner(), command.createdAt(), command.updatedAt());
        boardMapper.insertBoard(board);
        return Mono.just(board.getId());
    }

    @Transactional
    public Mono<Void> updateBoard(UpdateBoardCommand command) {
        return getBoardById(command.id())
                .doOnNext(board -> {
                    if (!board.getOwner().equals(command.owner())) {
                        throw new BoardException(ErrorType.UNAUTHORIZED);
                    }
                    board.update(command.title(), command.content());
                    boardMapper.updateBoard(board);
                })
                .then();
    }

    @Transactional
    public Mono<Void> deleteBoard(DeleteBoardCommand command) {
        return getBoardById(command.id())
                .doOnNext(board -> {
                    if (!board.getOwner().equals(command.owner())) {
                        throw new BoardException(ErrorType.UNAUTHORIZED);
                    }
                    boardMapper.deleteBoard(command.id());

                })
                .then();
    }
}
