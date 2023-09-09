package com.alpergayretoglu.chess_website_backend.service.chess;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;

public class ChessDefaultService {

    public static ChessCoordinate getDefaultKingCoordinate(ChessColor playerColor) {
        return playerColor == ChessColor.WHITE ? new ChessCoordinate(7, 4) : new ChessCoordinate(0, 4);
    }

    public static ChessCoordinate getDefaultShortCastlingRookCoordinate(ChessColor playerColor) {
        return playerColor == ChessColor.WHITE ? new ChessCoordinate(7, 7) : new ChessCoordinate(0, 7);
    }

    public static ChessCoordinate getDefaultLongCastlingRookCoordinate(ChessColor playerColor) {
        return playerColor == ChessColor.WHITE ? new ChessCoordinate(7, 0) : new ChessCoordinate(0, 0);
    }
}
