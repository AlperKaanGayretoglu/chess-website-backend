package com.alpergayretoglu.chess_website_backend.entity.chess;

public interface PieceMove {
    ChessCoordinate getFrom();

    ChessCoordinate getTo();
}
