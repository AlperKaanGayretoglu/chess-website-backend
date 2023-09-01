package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessBoard;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import com.alpergayretoglu.chess_website_backend.repository.ChessBoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class ChessBoardInitializer {

    private final ChessBoardRepository chessBoardRepository;

    public ChessBoard initializePieces(ChessBoard chessBoard) {
        Map<ChessCoordinate, ChessPiece> chessPieces = chessBoard.getChessPieces();
        chessPieces.clear();

        for (int column = 0; column < 8; column++) {
            chessPieces.put(new ChessCoordinate(1, column), ChessPiece.WHITE_PAWN);
            chessPieces.put(new ChessCoordinate(6, column), ChessPiece.BLACK_PAWN);
        }

        chessPieces.put(new ChessCoordinate(0, 0), ChessPiece.WHITE_ROOK);
        chessPieces.put(new ChessCoordinate(0, 1), ChessPiece.WHITE_KNIGHT);
        chessPieces.put(new ChessCoordinate(0, 2), ChessPiece.WHITE_BISHOP);
        chessPieces.put(new ChessCoordinate(0, 3), ChessPiece.WHITE_QUEEN);
        chessPieces.put(new ChessCoordinate(0, 4), ChessPiece.WHITE_KING);
        chessPieces.put(new ChessCoordinate(0, 5), ChessPiece.WHITE_BISHOP);
        chessPieces.put(new ChessCoordinate(0, 6), ChessPiece.WHITE_KNIGHT);
        chessPieces.put(new ChessCoordinate(0, 7), ChessPiece.WHITE_ROOK);

        chessPieces.put(new ChessCoordinate(7, 0), ChessPiece.BLACK_ROOK);
        chessPieces.put(new ChessCoordinate(7, 1), ChessPiece.BLACK_KNIGHT);
        chessPieces.put(new ChessCoordinate(7, 2), ChessPiece.BLACK_BISHOP);
        chessPieces.put(new ChessCoordinate(7, 3), ChessPiece.BLACK_QUEEN);
        chessPieces.put(new ChessCoordinate(7, 4), ChessPiece.BLACK_KING);
        chessPieces.put(new ChessCoordinate(7, 5), ChessPiece.BLACK_BISHOP);
        chessPieces.put(new ChessCoordinate(7, 6), ChessPiece.BLACK_KNIGHT);
        chessPieces.put(new ChessCoordinate(7, 7), ChessPiece.BLACK_ROOK);
        return chessBoard;
    }
}
