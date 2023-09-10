package com.alpergayretoglu.chess_website_backend.entity.chess.move.info;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import lombok.Data;

@Data
public class PieceTransformationMoveInfo {
    private final ChessCoordinate at;
    private final ChessPiece transformTo;
}
