package com.alpergayretoglu.chess_website_backend.model.response.chess;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessSquare;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChessSquareResponse {

    private final ChessColor chessColor;
    private final ChessPieceResponse chessPiece;

    public static ChessSquareResponse fromEntity(ChessSquare square) {
        ChessPieceResponse chessPieceResponse = square.isEmpty() ? null : ChessPieceResponse.fromEntity(square.getChessPiece());
        return ChessSquareResponse.builder()
                .chessColor(square.getChessColor())
                .chessPiece(chessPieceResponse)
                .build();
    }

}
