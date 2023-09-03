package com.alpergayretoglu.chess_website_backend.service.chess.legalMove;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.ChessMoveType;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.info.ChessMoveInfo;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.info.PlayedPieceMoveInfo;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessBoardPiecesObserver;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessMoveRegisterer;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class PawnLegalMoveService {
    static void calculateLegalMovesForPawn(
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            ChessMoveRegisterer chessMoveRegisterer
    ) {
        ChessColor pawnColor = chessBoardPiecesObserver.getChessPieceAt(currentCoordinate).getChessColor();

        boolean isPawnAtStartingPosition;

        int oneStepForwardRow;
        int twoStepsForwardRow;

        int rightDiagonalColumn;
        int leftDiagonalColumn;

        if (pawnColor == ChessColor.WHITE) {
            isPawnAtStartingPosition = currentCoordinate.getRow() == 6;
            oneStepForwardRow = currentCoordinate.getRow() - 1;
            twoStepsForwardRow = currentCoordinate.getRow() - 2;
            rightDiagonalColumn = currentCoordinate.getColumn() + 1;
            leftDiagonalColumn = currentCoordinate.getColumn() - 1;
        } else {
            isPawnAtStartingPosition = currentCoordinate.getRow() == 1;
            oneStepForwardRow = currentCoordinate.getRow() + 1;
            twoStepsForwardRow = currentCoordinate.getRow() + 2;
            rightDiagonalColumn = currentCoordinate.getColumn() - 1;
            leftDiagonalColumn = currentCoordinate.getColumn() + 1;
        }

        boolean isFirstMoveRegistered = registerPawnMove(
                currentCoordinate,
                oneStepForwardRow,
                chessBoardPiecesObserver,
                chessMoveRegisterer,
                ChessMoveType.NORMAL_PIECE_MOVEMENT
        );
        if (isFirstMoveRegistered && isPawnAtStartingPosition) {
            registerPawnMove(
                    currentCoordinate,
                    twoStepsForwardRow,
                    chessBoardPiecesObserver,
                    chessMoveRegisterer,
                    ChessMoveType.PAWN_DOUBLE_MOVEMENT
            );
        }

        registerPawnCapture(
                currentCoordinate,
                oneStepForwardRow,
                rightDiagonalColumn,
                chessBoardPiecesObserver,
                chessMoveRegisterer
        );

        registerPawnCapture(
                currentCoordinate,
                oneStepForwardRow,
                leftDiagonalColumn,
                chessBoardPiecesObserver,
                chessMoveRegisterer
        );

    }

    private static boolean registerPawnMove(
            ChessCoordinate initialCoordinate,
            int moveToRow,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessMoveRegisterer chessMoveRegisterer,
            ChessMoveType chessMoveType
    ) {
        if (!ChessCoordinate.isCoordinateValid(moveToRow, initialCoordinate.getColumn())) {
            return false;
        }

        ChessCoordinate toCoordinate = new ChessCoordinate(moveToRow, initialCoordinate.getColumn());
        if (chessBoardPiecesObserver.getChessPieceAt(toCoordinate) != null) {
            return false;
        }

        chessMoveRegisterer.registerNewChessMove(
                ChessMoveInfo.builder()
                        .playedPieceMoveInfo(new PlayedPieceMoveInfo(initialCoordinate, toCoordinate))
                        .chessMoveType(chessMoveType)
                        .build()
        );
        return true;
    }

    private static void registerPawnCapture(
            ChessCoordinate initialCoordinate,
            int moveToRow,
            int moveToColumn,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessMoveRegisterer chessMoveRegisterer
    ) {
        if (!ChessCoordinate.isCoordinateValid(moveToRow, moveToColumn)) {
            return;
        }

        ChessCoordinate toCoordinate = new ChessCoordinate(moveToRow, moveToColumn);
        ChessPiece potentialEnemyPiece = chessBoardPiecesObserver.getChessPieceAt(toCoordinate);
        if (potentialEnemyPiece == null || potentialEnemyPiece.getChessColor() == chessBoardPiecesObserver.getChessPieceAt(initialCoordinate).getChessColor()) {
            return;
        }

        chessMoveRegisterer.registerNewNormalCaptureMove(initialCoordinate, toCoordinate);
    }
}
