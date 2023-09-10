package com.alpergayretoglu.chess_website_backend.service.chess;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.PieceCaptureMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.PieceTransformationMove;
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

    public void playChessMove(
            PlayedPieceMove playedPieceMove,
            List<TriggeredPieceMove> triggeredPieceMoves,
            List<PieceCaptureMove> pieceCaptureMoves,
            PieceTransformationMove pieceTransformationMove
    ) {
        playPieceCaptures(pieceCaptureMoves);
        playTriggeredMoves(triggeredPieceMoves);
        playPieceMove(playedPieceMove);
        playPieceTransformation(pieceTransformationMove);
    }

    private void playPieceMove(PlayedPieceMove playedPieceMove) {
        ChessPiece chessPiece = removeChessPieceAt(playedPieceMove.getFrom());
        putChessPieceTo(playedPieceMove.getTo(), chessPiece);
    }

    private void playTriggeredMoves(List<TriggeredPieceMove> triggeredPieceMoves) {
        triggeredPieceMoves.forEach(triggeredPieceMove -> {
            ChessPiece chessPiece = removeChessPieceAt(triggeredPieceMove.getFrom());
            putChessPieceTo(triggeredPieceMove.getTo(), chessPiece);
        });
    }

    private void playPieceCaptures(List<PieceCaptureMove> pieceCaptureMoves) {
        pieceCaptureMoves.forEach(pieceCaptureMove -> removeChessPieceAt(pieceCaptureMove.getFrom()));
    }

    private void playPieceTransformation(PieceTransformationMove pieceTransformationMove) {
        if (pieceTransformationMove != null) {
            removeChessPieceAt(pieceTransformationMove.getAt());
            putChessPieceTo(pieceTransformationMove.getAt(), pieceTransformationMove.getTransformTo());
        }
    }

    public void clear() {
        chessPieces.clear();
    }

}
