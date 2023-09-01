package com.alpergayretoglu.chess_website_backend.entity.chess.chessPiece;

import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Getter
@Entity
@NoArgsConstructor
public class Rook extends ChessPiece {
    public Rook(ChessColor chessColor) {
        super(ChessPieceType.ROOK, chessColor);
    }
}
