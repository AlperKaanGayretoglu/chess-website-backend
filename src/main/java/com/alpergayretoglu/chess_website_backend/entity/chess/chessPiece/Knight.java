package com.alpergayretoglu.chess_website_backend.entity.chess.chessPiece;

import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import lombok.Getter;

@Getter
public class Knight extends ChessPiece {
    public Knight(ChessColor chessColor) {
        super(ChessPieceType.KNIGHT, chessColor);
    }
}
