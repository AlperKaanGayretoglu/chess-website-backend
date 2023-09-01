package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessBoard;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.chessPiece.*;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.repository.ChessBoardRepository;
import com.alpergayretoglu.chess_website_backend.repository.ChessPieceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class ChessBoardInitializer {

    private final ChessBoardRepository chessBoardRepository;
    private final ChessPieceRepository chessPieceRepository;

    public ChessBoard initializePieces(ChessBoard chessBoard) {
        Map<ChessCoordinate, ChessPiece> chessPieces = chessBoard.getChessPieces();
        chessPieces.clear();

        for (int column = 0; column < 8; column++) {
            chessPieces.put(new ChessCoordinate(1, column), new Pawn(ChessColor.WHITE));
            chessPieces.put(new ChessCoordinate(6, column), new Pawn(ChessColor.BLACK));
        }

        chessPieces.put(new ChessCoordinate(0, 0), new Rook(ChessColor.WHITE));
        chessPieces.put(new ChessCoordinate(0, 1), new Knight(ChessColor.WHITE));
        chessPieces.put(new ChessCoordinate(0, 2), new Bishop(ChessColor.WHITE));
        chessPieces.put(new ChessCoordinate(0, 3), new Queen(ChessColor.WHITE));
        chessPieces.put(new ChessCoordinate(0, 4), new King(ChessColor.WHITE));
        chessPieces.put(new ChessCoordinate(0, 5), new Bishop(ChessColor.WHITE));
        chessPieces.put(new ChessCoordinate(0, 6), new Knight(ChessColor.WHITE));
        chessPieces.put(new ChessCoordinate(0, 7), new Rook(ChessColor.WHITE));

        chessPieces.put(new ChessCoordinate(7, 0), new Rook(ChessColor.BLACK));
        chessPieces.put(new ChessCoordinate(7, 1), new Knight(ChessColor.BLACK));
        chessPieces.put(new ChessCoordinate(7, 2), new Bishop(ChessColor.BLACK));
        chessPieces.put(new ChessCoordinate(7, 3), new Queen(ChessColor.BLACK));
        chessPieces.put(new ChessCoordinate(7, 4), new King(ChessColor.BLACK));
        chessPieces.put(new ChessCoordinate(7, 5), new Bishop(ChessColor.BLACK));
        chessPieces.put(new ChessCoordinate(7, 6), new Knight(ChessColor.BLACK));
        chessPieces.put(new ChessCoordinate(7, 7), new Rook(ChessColor.BLACK));
        return chessBoard;
    }
}
