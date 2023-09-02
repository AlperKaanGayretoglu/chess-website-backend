package com.alpergayretoglu.chess_website_backend.model.enums;

import lombok.Getter;

@Getter
public enum ChessPieceType {
    PAWN(1),
    KNIGHT(3),
    BISHOP(3),
    ROOK(5),
    QUEEN(9),
    KING(100);

    private final int point;

    ChessPieceType(int point) {
        this.point = point;
    }


}
