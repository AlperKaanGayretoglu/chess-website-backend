package com.alpergayretoglu.chess_website_backend.model.response.chess;

import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChessPieceResponse {

    private final ChessPieceType chessPieceType;
    private final ChessColor chessColor;

    public static ChessPieceResponse fromEntity(ChessPiece chessPiece) {
        return ChessPieceResponse.builder()
                .chessPieceType(chessPiece.getChessPieceType())
                .chessColor(chessPiece.getChessColor())
                .build();
    }
}
