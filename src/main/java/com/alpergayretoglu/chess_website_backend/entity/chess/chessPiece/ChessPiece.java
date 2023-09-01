package com.alpergayretoglu.chess_website_backend.entity.chess.chessPiece;

import com.alpergayretoglu.chess_website_backend.entity.BaseEntity;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor
@Getter
@Entity
public abstract class ChessPiece extends BaseEntity {

    private ChessPieceType chessPieceType;
    private ChessColor chessColor;

    public ChessPiece(ChessPieceType chessPieceType, ChessColor chessColor) {
        super();
        this.chessPieceType = chessPieceType;
        this.chessColor = chessColor;
    }

}
