package com.alpergayretoglu.chess_website_backend.entity.chess.chessPiece;

import com.alpergayretoglu.chess_website_backend.entity.BaseEntity;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public abstract class ChessPiece extends BaseEntity {

    @Delegate
    private ChessPieceType chessPieceType;
    private ChessColor chessColor;

}
