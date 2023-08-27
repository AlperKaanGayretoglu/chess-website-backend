package com.alpergayretoglu.chess_website_backend.model.enums;

public enum ChessColor {
    WHITE,
    BLACK;

    public ChessColor getOppositeColor() {
        return this == WHITE ? BLACK : WHITE;
    }
    
}
