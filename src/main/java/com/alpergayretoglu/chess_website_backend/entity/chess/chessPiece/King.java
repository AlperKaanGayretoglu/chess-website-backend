package com.alpergayretoglu.chess_website_backend.entity.chess.chessPiece;

import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import lombok.Getter;

@Getter
public class King extends ChessPiece {
    public King(ChessColor chessColor) {
        super(ChessPieceType.KING, chessColor);
    }
}
