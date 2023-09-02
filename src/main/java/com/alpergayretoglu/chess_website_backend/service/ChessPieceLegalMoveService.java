package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.pattern.PiecePattern;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.alpergayretoglu.chess_website_backend.entity.chess.pattern.PiecePattern.chessPieceBasicMovePatternMap;

@Service
@NoArgsConstructor
public class ChessPieceLegalMoveService {
    @FunctionalInterface
    private interface ChessPieceLegalMoveCalculator {
        void calculateLegalMoves(
                ChessBoardPiecesObserver chessBoardPiecesObserver,
                ChessCoordinate currentCoordinate,
                ChessMoveRegisterer chessMoveRegisterer
        );
    }

    private static final Map<ChessPieceType, ChessPieceLegalMoveCalculator> chessPieceLegalMoveCalculatorMap;

    static {
        chessPieceLegalMoveCalculatorMap = new HashMap<>();
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.PAWN, ChessPieceLegalMoveService::calculateLegalMovesForPawn);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.ROOK, ChessPieceLegalMoveService::calculateBasicMovement);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.KNIGHT, ChessPieceLegalMoveService::calculateBasicMovement);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.BISHOP, ChessPieceLegalMoveService::calculateBasicMovement);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.QUEEN, ChessPieceLegalMoveService::calculateBasicMovement);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.KING, ChessPieceLegalMoveService::calculateLegalMovesForKing);
    }

    public void calculateLegalMovesForPieceAtSquare(
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            ChessMoveRegisterer chessMoveRegisterer
    ) {
        chessPieceLegalMoveCalculatorMap.get(chessBoardPiecesObserver.getChessPieceAt(currentCoordinate).getChessPieceType())
                .calculateLegalMoves(chessBoardPiecesObserver, currentCoordinate, chessMoveRegisterer);
    }

    private static void calculateBasicMovement(
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            ChessMoveRegisterer chessMoveRegisterer
    ) {
        ChessPiece chessPiece = chessBoardPiecesObserver.getChessPieceAt(currentCoordinate);

        PiecePattern piecePattern = chessPieceBasicMovePatternMap.get(chessPiece.getChessPieceType());
        piecePattern.alongEachDirectionStartingFromWhileDo(
                currentCoordinate,
                chessCoordinate -> {
                    ChessPiece chessPieceAt = chessBoardPiecesObserver.getChessPieceAt(chessCoordinate);
                    if (chessPieceAt == null) {
                        return true;
                    }
                    if (chessPieceAt.getChessColor() == chessPiece.getChessColor()) {
                        return false;
                    }
                    return true;
                },
                chessCoordinate -> chessMoveRegisterer.registerNewChessMove(currentCoordinate, chessCoordinate)
        );
    }

    private static void calculateLegalMovesForPawn(
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            ChessMoveRegisterer chessMoveRegisterer
    ) {
        ChessColor pawnColor = chessBoardPiecesObserver.getChessPieceAt(currentCoordinate).getChessColor();

        boolean isPawnAtStartingPosition;
        int oneStepForwardRow;
        int twoStepsForwardRow;

        if (pawnColor == ChessColor.WHITE) {
            isPawnAtStartingPosition = currentCoordinate.getRow() == 6;
            oneStepForwardRow = currentCoordinate.getRow() - 1;
            twoStepsForwardRow = currentCoordinate.getRow() - 2;
        } else {
            isPawnAtStartingPosition = currentCoordinate.getRow() == 1;
            oneStepForwardRow = currentCoordinate.getRow() + 1;
            twoStepsForwardRow = currentCoordinate.getRow() + 2;
        }

        boolean isFirstMoveRegistered = registerPawnMove(currentCoordinate, oneStepForwardRow, chessBoardPiecesObserver, chessMoveRegisterer);
        if (isFirstMoveRegistered && isPawnAtStartingPosition) {
            registerPawnMove(currentCoordinate, twoStepsForwardRow, chessBoardPiecesObserver, chessMoveRegisterer);
        }

    }

    private static boolean registerPawnMove(
            ChessCoordinate initialCoordinate,
            int moveToRow,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessMoveRegisterer chessMoveRegisterer
    ) {
        if (!ChessCoordinate.isCoordinateValid(moveToRow, initialCoordinate.getColumn())) {
            return false;
        }

        ChessCoordinate toCoordinate = new ChessCoordinate(moveToRow, initialCoordinate.getColumn());
        if (chessBoardPiecesObserver.getChessPieceAt(toCoordinate) != null) {
            return false;
        }

        chessMoveRegisterer.registerNewChessMove(initialCoordinate, toCoordinate);
        return true;
    }

    private static void calculateLegalMovesForKing(
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            ChessMoveRegisterer chessMoveRegisterer
    ) {
        // TODO: Remove this method and implement it separately
        calculateBasicMovement(chessBoardPiecesObserver, currentCoordinate, chessMoveRegisterer);
    }

}
