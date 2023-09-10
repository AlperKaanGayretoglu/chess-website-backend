package com.alpergayretoglu.chess_website_backend.model.response.chess;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.PieceTransformationMove;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PieceTransformationMoveResponse {

    private ChessCoordinate at;
    private ChessPieceResponse transformTo;

    public static PieceTransformationMoveResponse fromEntity(PieceTransformationMove transformationMove) {
        if (transformationMove == null) {
            return null;
        }
        return PieceTransformationMoveResponse.builder()
                .at(transformationMove.getAt())
                .transformTo(ChessPieceResponse.fromEntity(transformationMove.getTransformTo()))
                .build();
    }
}
