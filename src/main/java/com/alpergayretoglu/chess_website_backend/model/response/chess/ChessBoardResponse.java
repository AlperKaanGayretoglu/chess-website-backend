package com.alpergayretoglu.chess_website_backend.model.response.chess;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;

@Data
@Builder
@AllArgsConstructor
public class ChessBoardResponse {

    private final ChessSquareResponse[][] board;

    public static ChessBoardResponse fromEntity(ChessBoard board) {
        return ChessBoardResponse.builder()
                .board(Arrays.stream(board.getBoard())
                        .map(row -> Arrays.stream(row).map(ChessSquareResponse::fromEntity)
                                .toArray(ChessSquareResponse[]::new))
                        .toArray(ChessSquareResponse[][]::new))
                .build();
    }

}
