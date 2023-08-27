package com.alpergayretoglu.chess_website_backend.entity.chess;

import com.alpergayretoglu.chess_website_backend.entity.BaseEntity;
import com.alpergayretoglu.chess_website_backend.entity.chess.chessPiece.ChessPiece;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
public class ChessSquare extends BaseEntity {

    private final ChessColor chessColor;
    private ChessPiece chessPiece;


    public ChessPiece removeChessPiece() {
        ChessPiece chessPiece = getChessPiece();
        setChessPiece(null);
        return chessPiece;
    }

    public boolean isEmpty() {
        return chessPiece == null;
    }

}
