package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.PieceCaptureMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.PlayedPieceMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.TriggeredPieceMove;
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

    public ChessPiece removeChessPieceAt(ChessCoordinate chessCoordinate) {
        return chessPieces.remove(chessCoordinate);
    }

    public void putChessPieceTo(ChessCoordinate chessCoordinate, ChessPiece chessPiece) {
        chessPieces.put(chessCoordinate, chessPiece);
    }

    public void playPieceMove(PlayedPieceMove playedPieceMove) {
        ChessPiece chessPiece = removeChessPieceAt(playedPieceMove.getFrom());
        putChessPieceTo(playedPieceMove.getTo(), chessPiece);
    }

    public void playTriggeredMoves(List<TriggeredPieceMove> triggeredPieceMoves) {
        triggeredPieceMoves.forEach(triggeredPieceMove -> {
            ChessPiece chessPiece = removeChessPieceAt(triggeredPieceMove.getFrom());
            putChessPieceTo(triggeredPieceMove.getTo(), chessPiece);
        });
    }

    public void playPieceCaptures(List<PieceCaptureMove> pieceCaptureMoves) {
        pieceCaptureMoves.forEach(pieceCaptureMove -> removeChessPieceAt(pieceCaptureMove.getFrom()));
    }

    public void clear() {
        chessPieces.clear();
    }

}
