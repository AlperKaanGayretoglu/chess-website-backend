package com.alpergayretoglu.chess_website_backend.service.chess.legalMove;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.ChessMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.ChessMoveType;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.info.ChessMoveInfo;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.info.PieceCaptureMoveInfo;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.info.PlayedPieceMoveInfo;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessBoardPiecesObserver;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessMoveRegisterer;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@NoArgsConstructor
public class PawnLegalMoveService {
    static void calculateLegalMovesForPawn(
            boolean isCurrentPlayerInCheck,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            ChessMoveRegisterer chessMoveRegisterer,
            @Nullable ChessMove lastPlayedChessMove
    ) {
        ChessColor pawnColor = chessBoardPiecesObserver.getChessPieceAt(currentCoordinate).getChessColor();

        boolean isPawnAtStartingPosition;

        int oneStepForwardRow;
        int twoStepsForwardRow;

        int rightColumn;
        int leftColumn;

        if (pawnColor == ChessColor.WHITE) {
            isPawnAtStartingPosition = currentCoordinate.getRow() == 6;
            oneStepForwardRow = currentCoordinate.getRow() - 1;
            twoStepsForwardRow = currentCoordinate.getRow() - 2;
            rightColumn = currentCoordinate.getColumn() + 1;
            leftColumn = currentCoordinate.getColumn() - 1;
        } else {
            isPawnAtStartingPosition = currentCoordinate.getRow() == 1;
            oneStepForwardRow = currentCoordinate.getRow() + 1;
            twoStepsForwardRow = currentCoordinate.getRow() + 2;
            rightColumn = currentCoordinate.getColumn() - 1;
            leftColumn = currentCoordinate.getColumn() + 1;
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
                rightColumn,
                chessBoardPiecesObserver,
                chessMoveRegisterer
        );

        registerPawnCapture(
                currentCoordinate,
                oneStepForwardRow,
                leftColumn,
                chessBoardPiecesObserver,
                chessMoveRegisterer
        );

        registerEnPassantCapture(
                currentCoordinate,
                oneStepForwardRow,
                rightColumn,
                leftColumn,
                chessMoveRegisterer,
                lastPlayedChessMove
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

    private static void registerEnPassantCapture(
            ChessCoordinate initialCoordinate,
            int moveToRow,
            int rightOfPawnColumn,
            int leftOfPawnColumn,
            ChessMoveRegisterer chessMoveRegisterer,
            ChessMove lastPlayedChessMove
    ) {
        if (lastPlayedChessMove == null || lastPlayedChessMove.getChessMoveType() != ChessMoveType.PAWN_DOUBLE_MOVEMENT) {
            return;
        }

        ChessCoordinate lastPlayedMoveToCoordinate = lastPlayedChessMove.getPlayedPieceMove().getTo();
        if (lastPlayedMoveToCoordinate.getRow() != initialCoordinate.getRow()) {
            return;
        }

        int column = lastPlayedMoveToCoordinate.getColumn() == rightOfPawnColumn ? rightOfPawnColumn
                : lastPlayedMoveToCoordinate.getColumn() == leftOfPawnColumn ? leftOfPawnColumn
                : -1;

        if (column == -1) {
            return;
        }

        chessMoveRegisterer.registerNewChessMove(
                ChessMoveInfo.builder()
                        .playedPieceMoveInfo(new PlayedPieceMoveInfo(initialCoordinate, new ChessCoordinate(moveToRow, column)))
                        .pieceCaptureMoveInfos(Collections.singletonList(
                                new PieceCaptureMoveInfo(lastPlayedChessMove.getPlayedPieceMove().getTo()))
                        ).chessMoveType(ChessMoveType.PAWN_EN_PASSANT_CAPTURE)
                        .build()

        );
    }
}
