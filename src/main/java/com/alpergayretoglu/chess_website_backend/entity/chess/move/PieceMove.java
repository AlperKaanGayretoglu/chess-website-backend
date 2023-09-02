package com.alpergayretoglu.chess_website_backend.entity.chess.move;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;

public interface PieceMove {
    ChessCoordinate getFrom();

    ChessCoordinate getTo();
}
