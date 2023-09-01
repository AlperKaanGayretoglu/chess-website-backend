package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.chess.*;
import com.alpergayretoglu.chess_website_backend.entity.chess.movePattern.MovePatternIterator;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import com.alpergayretoglu.chess_website_backend.repository.ChessGameRepository;
import com.alpergayretoglu.chess_website_backend.repository.ChessMoveRepository;
import com.alpergayretoglu.chess_website_backend.repository.PlayedPieceMoveRepository;
import com.alpergayretoglu.chess_website_backend.repository.TriggeredPieceMoveRepository;
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

    private final ChessMoveRepository chessMoveRepository;
    private final PlayedPieceMoveRepository playedPieceMoveRepository;
    private final TriggeredPieceMoveRepository triggeredPieceMoveRepository;

    private final ChessGameRepository chessGameRepository;

    public void calculateAndSaveLegalMovesForPieceAtSquare(ChessBoardPiecesObserver chessBoardPiecesObserver, ChessGame chessGame, ChessCoordinate currentCoordinate) {
        ChessPiece chessPiece = chessBoardPiecesObserver.getChessPieceAt(currentCoordinate);

        if (chessPiece == null) {
            return;
        }

        List<ChessMove> legalMoves = new ArrayList<>();
        List<PlayedPieceMove> playedPieceMoves = new ArrayList<>();

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

        chessMoveRepository.saveAll(legalMoves);
        playedPieceMoveRepository.saveAll(playedPieceMoves);

        chessGameRepository.save(chessGame);
    }

}
