package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessBoard;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.PlayedPieceMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.chessPiece.ChessPiece;
import com.alpergayretoglu.chess_website_backend.entity.chess.movePattern.MovePatternIterator;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
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

    public List<ChessMove> calculateLegalMovesForPieceAtSquare(ChessBoard chessBoard, ChessCoordinate currentCoordinate) {
        ChessPiece chessPiece = chessBoard.getChessPieceAt(currentCoordinate);
        List<ChessMove> legalMoves = new ArrayList<>();

        if (chessPiece == null) {
            return legalMoves;
        }

        MovePatternIterator movePatternIterator = chessPiece.getMovePattern().getIteratorStartingAt(currentCoordinate);
        while (movePatternIterator.hasNext()) {
            ChessCoordinate nextCoordinate = movePatternIterator.next();

            PlayedPieceMove playedPieceMove = new PlayedPieceMove(null, currentCoordinate, nextCoordinate);
            playedPieceMoveRepository.save(playedPieceMove);

            ChessMove chessMove = ChessMove.builder()
                    .build();
            chessMoveRepository.save(chessMove);

            playedPieceMove.setPartOfChessMove(chessMove);
            playedPieceMoveRepository.save(playedPieceMove);

            if (chessBoard.getChessPieceAt(nextCoordinate) == null) {
                legalMoves.add(chessMove);
                continue;
            }

            if (chessBoard.getChessPieceAt(nextCoordinate).getChessColor() != chessPiece.getChessColor()) {
                legalMoves.add(chessMove);
            }

        }

        return legalMoves;
    }

}
