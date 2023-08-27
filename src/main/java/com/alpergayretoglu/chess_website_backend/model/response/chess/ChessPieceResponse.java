package com.alpergayretoglu.chess_website_backend.model.response.chess;

import com.alpergayretoglu.chess_website_backend.entity.chess.chessPiece.ChessPiece;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import com.alpergayretoglu.chess_website_backend.model.response.EntityResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class ChessPieceResponse extends EntityResponse {

    private final ChessPieceType chessPieceType;
    private final ChessColor chessColor;

    public static ChessPieceResponse fromEntity(ChessPiece chessPiece) {
        ChessPieceResponse response = ChessPieceResponse.builder()
                .chessPieceType(chessPiece.getChessPieceType())
                .chessColor(chessPiece.getChessColor())
                .build();

        return setCommonValuesFromEntity(response, chessPiece);
    }
}
