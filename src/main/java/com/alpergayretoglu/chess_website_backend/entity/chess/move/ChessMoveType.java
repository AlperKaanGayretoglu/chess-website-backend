package com.alpergayretoglu.chess_website_backend.entity.chess.move;

public enum ChessMoveType {
    NORMAL_PIECE_MOVEMENT,
    PAWN_DOUBLE_MOVEMENT,

    NORMAL_PIECE_CAPTURE,
    PAWN_EN_PASSANT_CAPTURE,

    SHORT_CASTLING,
    LONG_CASTLING,
}
