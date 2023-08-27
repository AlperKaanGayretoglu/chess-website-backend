package com.alpergayretoglu.chess_website_backend.model.enums;

import lombok.Getter;

@Getter
public enum ChessGameStatus {

    ONGOING(false),

    WHITE_WON_BY_CHECKMATE(true),
    BLACK_WON_BY_CHECKMATE(true),

    WHITE_WON_BY_RESIGNATION(true),
    BLACK_WON_BY_RESIGNATION(true),

    WHITE_WON_BY_TIMEOUT(true),
    BLACK_WON_BY_TIMEOUT(true),

    DRAW_BY_THREE_FOLD_REPETITION(true),
    DRAW_BY_FIFTY_MOVE(true),
    DRAW_BY_INSUFFICIENT_MATERIAL(true),
    DRAW_BY_AGREEMENT(true);

    private final boolean isGameEnded;

    ChessGameStatus(boolean hasGameEnded) {
        this.isGameEnded = hasGameEnded;
    }

}
