package com.alpergayretoglu.chess_website_backend.model.response.chess;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessBoard;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class ChessBoardResponse {

    private final Map<ChessCoordinate, ChessPieceResponse> board;

    public static ChessBoardResponse fromEntity(ChessBoard chessBoard) {
        return ChessBoardResponse.builder()
                .board(chessBoard.getChessPieces().entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> ChessPieceResponse.fromEntity(entry.getValue())
                        )))
                .build();
    }

}
