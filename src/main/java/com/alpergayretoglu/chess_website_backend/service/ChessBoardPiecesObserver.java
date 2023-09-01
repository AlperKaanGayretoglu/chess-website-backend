package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ChessBoardPiecesObserver {

    private final Map<ChessCoordinate, ChessPiece> chessPieces;

    public ChessPiece getChessPieceAt(ChessCoordinate chessCoordinate) {
        return chessPieces.get(chessCoordinate);
    }

    public List<ChessCoordinate> getCoordinatesOfPiecesWithColor(ChessColor chessColor) {
        List<ChessCoordinate> chessCoordinates = new ArrayList<>();

        for (Map.Entry<ChessCoordinate, ChessPiece> entry : chessPieces.entrySet()) {
            if (entry.getValue().getChessColor() == chessColor) {
                chessCoordinates.add(entry.getKey());
            }
        }

        return chessCoordinates;
    }

}
