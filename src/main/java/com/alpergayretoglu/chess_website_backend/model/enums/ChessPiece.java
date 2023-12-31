package com.alpergayretoglu.chess_website_backend.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum ChessPiece {

    WHITE_PAWN(ChessPieceType.PAWN, ChessColor.WHITE),
    WHITE_KNIGHT(ChessPieceType.KNIGHT, ChessColor.WHITE),
    WHITE_BISHOP(ChessPieceType.BISHOP, ChessColor.WHITE),
    WHITE_ROOK(ChessPieceType.ROOK, ChessColor.WHITE),
    WHITE_QUEEN(ChessPieceType.QUEEN, ChessColor.WHITE),
    WHITE_KING(ChessPieceType.KING, ChessColor.WHITE),

    BLACK_PAWN(ChessPieceType.PAWN, ChessColor.BLACK),
    BLACK_KNIGHT(ChessPieceType.KNIGHT, ChessColor.BLACK),
    BLACK_BISHOP(ChessPieceType.BISHOP, ChessColor.BLACK),
    BLACK_ROOK(ChessPieceType.ROOK, ChessColor.BLACK),
    BLACK_QUEEN(ChessPieceType.QUEEN, ChessColor.BLACK),
    BLACK_KING(ChessPieceType.KING, ChessColor.BLACK);

    private final ChessPieceType chessPieceType;
    private final ChessColor chessColor;

    ChessPiece(ChessPieceType chessPieceType, ChessColor chessColor) {
        this.chessPieceType = chessPieceType;
        this.chessColor = chessColor;
    }

    public static List<ChessPiece> getAllPiecesWithColor(ChessColor chessColor) {
        return Arrays.stream(ChessPiece.values())
                .filter(chessPiece -> chessPiece.getChessColor() == chessColor)
                .collect(Collectors.toList());
    }

    // make it so that its json representation is an object with two fields: color and type


}
