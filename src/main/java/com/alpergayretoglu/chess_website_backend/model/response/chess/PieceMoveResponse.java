package com.alpergayretoglu.chess_website_backend.model.response.chess;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.PieceMove;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PieceMoveResponse {

    private final ChessCoordinate from;

    private final ChessCoordinate to;

    public static PieceMoveResponse fromEntity(PieceMove pieceMove) {
        if (pieceMove == null) {
            return null;
        }
        return PieceMoveResponse.builder()
                .from(pieceMove.getFrom())
                .to(pieceMove.getTo())
                .build();
    }

}
