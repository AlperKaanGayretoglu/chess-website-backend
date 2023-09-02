package com.alpergayretoglu.chess_website_backend.model.response.chess;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.PieceCaptureMove;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PieceCaptureMoveResponse {
    private final ChessCoordinate from;

    public static PieceCaptureMoveResponse fromEntity(PieceCaptureMove captureMove) {
        if (captureMove == null) {
            return null;
        }
        return PieceCaptureMoveResponse.builder()
                .from(captureMove.getFrom())
                .build();
    }
}
