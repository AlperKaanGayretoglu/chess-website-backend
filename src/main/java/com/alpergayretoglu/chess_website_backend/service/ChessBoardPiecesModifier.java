package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.PlayedPieceMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.TriggeredPieceMove;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ChessBoardPiecesModifier {

    private final Map<ChessCoordinate, ChessPiece> chessPieces;

    public void clear() {
        chessPieces.clear();
    }

    public ChessPiece getChessPieceAt(ChessCoordinate chessCoordinate) {
        return chessPieces.get(chessCoordinate);
    }

    public ChessPiece removeChessPieceAt(ChessCoordinate chessCoordinate) {
        return chessPieces.remove(chessCoordinate);
    }

    public void putChessPieceTo(ChessCoordinate chessCoordinate, ChessPiece chessPiece) {
        chessPieces.put(chessCoordinate, chessPiece);
    }

    public void playMove(PlayedPieceMove playedPieceMove) {
        ChessPiece chessPiece = removeChessPieceAt(playedPieceMove.getFrom());
        putChessPieceTo(playedPieceMove.getTo(), chessPiece);
    }

    public void playTriggeredMoves(List<TriggeredPieceMove> triggeredPieceMoves) {
        triggeredPieceMoves.forEach(triggeredPieceMove -> {
            ChessPiece chessPiece = removeChessPieceAt(triggeredPieceMove.getFrom());
            putChessPieceTo(triggeredPieceMove.getTo(), chessPiece);
        });
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
