package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.chess.*;
import com.alpergayretoglu.chess_website_backend.entity.chess.movePattern.MovePatternIterator;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ChessPieceLegalMoveService {
    @FunctionalInterface
    private interface ChessPieceLegalMoveCalculator {
        List<ChessMove> calculateLegalMoves(ChessBoard chessBoard, ChessCoordinate currentCoordinate);
    }

    private static final Map<ChessPieceType, ChessPieceLegalMoveCalculator> chessPieceLegalMoveCalculatorMap;

    static {
        chessPieceLegalMoveCalculatorMap = new HashMap<>();
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.PAWN, (chessBoard, currentCoordinate) -> new ArrayList<>());
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.ROOK, (chessBoard, currentCoordinate) -> new ArrayList<>());
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.KNIGHT, (chessBoard, currentCoordinate) -> new ArrayList<>());
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.BISHOP, (chessBoard, currentCoordinate) -> new ArrayList<>());
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.QUEEN, (chessBoard, currentCoordinate) -> new ArrayList<>());
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.KING, (chessBoard, currentCoordinate) -> new ArrayList<>());
    }

    public void calculateLegalMovesForPieceAtSquare(
            ChessGame chessGame,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            List<ChessMove> legalMoves,
            List<PlayedPieceMove> playedPieceMoves
    ) {
        ChessPiece chessPiece = chessBoardPiecesObserver.getChessPieceAt(currentCoordinate);

        MovePatternIterator movePatternIterator = chessPiece.getChessPieceType().getMovePattern().getIteratorStartingAt(currentCoordinate);

        while (movePatternIterator.hasNext()) {
            ChessCoordinate nextCoordinate = movePatternIterator.next();

            ChessMove chessMove = ChessMove.builder().chessGame(chessGame).build();

            if (
                    chessBoardPiecesObserver.getChessPieceAt(nextCoordinate) == null ||
                            chessBoardPiecesObserver.getChessPieceAt(nextCoordinate).getChessColor() != chessPiece.getChessColor()
            ) {
                legalMoves.add(chessMove);

                PlayedPieceMove playedPieceMove = new PlayedPieceMove(chessMove, currentCoordinate, nextCoordinate);
                playedPieceMoves.add(playedPieceMove);
            }
        }
    }

}
