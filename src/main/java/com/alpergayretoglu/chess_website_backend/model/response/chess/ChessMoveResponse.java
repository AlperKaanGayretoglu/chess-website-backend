package com.alpergayretoglu.chess_website_backend.model.response.chess;

import com.alpergayretoglu.chess_website_backend.entity.chess.move.ChessMoveType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ChessMoveResponse {

    private String id;

    private PieceMoveResponse playedPieceMove;
    private List<PieceMoveResponse> triggeredPieceMoves;
    private List<PieceCaptureMoveResponse> pieceCaptureMoves;

    private ChessMoveType moveType;

}
