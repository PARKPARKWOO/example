package com.example.hecto.mapper;

import com.example.hecto.model.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<Board> getAllBoards(
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("sortBy") String sortBy,
            @Param("sortOrder") String sortOrder
    );

    Board getBoardById(Long id);

    Long insertBoard(Board board);

    void updateBoard(Board board);

    void deleteBoard(Long id);
}