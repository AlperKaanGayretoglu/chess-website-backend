package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChessBoardInitializer {

    public void initializePieces(ChessBoardPiecesModifier chessBoardPiecesModifier) {
        chessBoardPiecesModifier.clear();

        for (int column = 0; column < 8; column++) {
            chessBoardPiecesModifier.putChessPieceTo(new ChessCoordinate(1, column), ChessPiece.WHITE_PAWN);
            chessBoardPiecesModifier.putChessPieceTo(new ChessCoordinate(6, column), ChessPiece.BLACK_PAWN);
        }

        chessBoardPiecesModifier.putChessPieceTo(new ChessCoordinate(0, 0), ChessPiece.WHITE_ROOK);
        chessBoardPiecesModifier.putChessPieceTo(new ChessCoordinate(0, 1), ChessPiece.WHITE_KNIGHT);
        chessBoardPiecesModifier.putChessPieceTo(new ChessCoordinate(0, 2), ChessPiece.WHITE_BISHOP);
        chessBoardPiecesModifier.putChessPieceTo(new ChessCoordinate(0, 3), ChessPiece.WHITE_QUEEN);
        chessBoardPiecesModifier.putChessPieceTo(new ChessCoordinate(0, 4), ChessPiece.WHITE_KING);
        chessBoardPiecesModifier.putChessPieceTo(new ChessCoordinate(0, 5), ChessPiece.WHITE_BISHOP);
        chessBoardPiecesModifier.putChessPieceTo(new ChessCoordinate(0, 6), ChessPiece.WHITE_KNIGHT);
        chessBoardPiecesModifier.putChessPieceTo(new ChessCoordinate(0, 7), ChessPiece.WHITE_ROOK);

        chessBoardPiecesModifier.putChessPieceTo(new ChessCoordinate(7, 0), ChessPiece.BLACK_ROOK);
        chessBoardPiecesModifier.putChessPieceTo(new ChessCoordinate(7, 1), ChessPiece.BLACK_KNIGHT);
        chessBoardPiecesModifier.putChessPieceTo(new ChessCoordinate(7, 2), ChessPiece.BLACK_BISHOP);
        chessBoardPiecesModifier.putChessPieceTo(new ChessCoordinate(7, 3), ChessPiece.BLACK_QUEEN);
        chessBoardPiecesModifier.putChessPieceTo(new ChessCoordinate(7, 4), ChessPiece.BLACK_KING);
        chessBoardPiecesModifier.putChessPieceTo(new ChessCoordinate(7, 5), ChessPiece.BLACK_BISHOP);
        chessBoardPiecesModifier.putChessPieceTo(new ChessCoordinate(7, 6), ChessPiece.BLACK_KNIGHT);
        chessBoardPiecesModifier.putChessPieceTo(new ChessCoordinate(7, 7), ChessPiece.BLACK_ROOK);
    }
}
