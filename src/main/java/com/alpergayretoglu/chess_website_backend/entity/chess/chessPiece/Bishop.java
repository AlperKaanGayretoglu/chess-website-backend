package com.alpergayretoglu.chess_website_backend.entity.chess.chessPiece;

import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Getter
@Entity
@NoArgsConstructor
public class Bishop extends ChessPiece {
    public Bishop(ChessColor chessColor) {
        super(ChessPieceType.BISHOP, chessColor);
    }
}
