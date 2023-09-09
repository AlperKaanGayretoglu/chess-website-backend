package com.alpergayretoglu.chess_website_backend.model.enums;

import lombok.Getter;

@Getter
public enum ChessGameStatus {

    ONGOING(false),

    WHITE_WON_BY_CHECKMATE(true),
    BLACK_WON_BY_CHECKMATE(true),

    DRAW_BY_STALEMATE(true);

    private final boolean hasGameEnded;

    ChessGameStatus(boolean hasGameEnded) {
        this.hasGameEnded = hasGameEnded;
    }

}
