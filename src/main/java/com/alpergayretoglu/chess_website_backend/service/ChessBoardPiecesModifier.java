package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.PlayedPieceMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.TriggeredPieceMove;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;

import java.util.List;
import java.util.Map;

public class ChessBoardPiecesModifier {

    private final Map<ChessCoordinate, ChessPiece> chessPieces;
    private final ChessBoardPiecesObserver chessBoardPiecesObserver;

    public ChessBoardPiecesModifier(Map<ChessCoordinate, ChessPiece> chessPieces) {
        this.chessPieces = chessPieces;
        this.chessBoardPiecesObserver = new ChessBoardPiecesObserver(chessPieces);
    }

    public ChessBoardPiecesObserver turnIntoObserver() {
        return chessBoardPiecesObserver;
    }

    public ChessPiece getChessPieceAt(ChessCoordinate chessCoordinate) {
        return chessBoardPiecesObserver.getChessPieceAt(chessCoordinate);
    }

    public List<ChessCoordinate> getCoordinatesOfPiecesWithColor(ChessColor chessColor) {
        return chessBoardPiecesObserver.getCoordinatesOfPiecesWithColor(chessColor);
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

    public void clear() {
        chessPieces.clear();
    }

}
