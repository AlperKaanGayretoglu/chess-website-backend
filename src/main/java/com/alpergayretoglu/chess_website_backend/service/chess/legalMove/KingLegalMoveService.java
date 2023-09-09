package com.alpergayretoglu.chess_website_backend.service.chess.legalMove;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.ChessMoveType;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.info.ChessMoveInfo;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.info.PlayedPieceMoveInfo;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.info.TriggeredPieceMoveInfo;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessBoardPiecesObserver;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessDefaultService;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessMoveRegisterer;
import com.alpergayretoglu.chess_website_backend.service.chess.legalMove.options.LegalMoveCalculatorOptions;

import java.util.Collections;

import static com.alpergayretoglu.chess_website_backend.service.chess.legalMove.ChessPieceLegalMoveService.afterThisMoveIsPlayedCanTheKingBeTakenByEnemy;
import static com.alpergayretoglu.chess_website_backend.service.chess.legalMove.ChessPieceLegalMoveService.calculateBasicMovement;

public class KingLegalMoveService {

    public static void calculateLegalMovesForKing(LegalMoveCalculatorOptions options) {
        calculateBasicMovement(options);

        if (options.isCurrentPlayerInCheck()) {
            return;
        }

        ChessBoardPiecesObserver chessBoardPiecesObserver = options.getChessBoardPiecesObserver();
        ChessCoordinate currentCoordinate = options.getForPieceAtCoordinate();
        ChessMoveRegisterer chessMoveRegisterer = options.getChessMoveRegisterer();

        ChessPiece theKing = chessBoardPiecesObserver.getChessPieceAt(currentCoordinate);
        ChessColor color = theKing.getChessColor();

        ChessPiece potentialKing = chessBoardPiecesObserver.getChessPieceAt(ChessDefaultService.getDefaultKingCoordinate(color));

        if (potentialKing != theKing) {
            return;
        }

        ChessCoordinate defaultShortCastlingRookCoordinate = ChessDefaultService.getDefaultShortCastlingRookCoordinate(color);
        ChessCoordinate defaultLongCastlingRookCoordinate = ChessDefaultService.getDefaultLongCastlingRookCoordinate(color);

        ChessPiece potentialShortCastlingRook = chessBoardPiecesObserver.getChessPieceAt(defaultShortCastlingRookCoordinate);
        ChessPiece potentialLongCastlingRook = chessBoardPiecesObserver.getChessPieceAt(defaultLongCastlingRookCoordinate);

        boolean canPotentiallyShortCastle =
                potentialShortCastlingRook != null &&
                        potentialShortCastlingRook.getChessColor() == color &&
                        potentialShortCastlingRook.getChessPieceType() == ChessPieceType.ROOK;

        boolean canPotentiallyLongCastle =
                potentialLongCastlingRook != null &&
                        potentialLongCastlingRook.getChessColor() == color &&
                        potentialLongCastlingRook.getChessPieceType() == ChessPieceType.ROOK;

        ChessCoordinate oneStepTowardsShortCastle;
        ChessCoordinate twoStepsTowardsShortCastle;

        ChessCoordinate oneStepTowardsLongCastle;
        ChessCoordinate twoStepsTowardsLongCastle;
        ChessCoordinate threeStepsTowardsLongCastle;

        if (color == ChessColor.WHITE) {
            oneStepTowardsShortCastle = new ChessCoordinate(7, 5);
            twoStepsTowardsShortCastle = new ChessCoordinate(7, 6);

            oneStepTowardsLongCastle = new ChessCoordinate(7, 3);
            twoStepsTowardsLongCastle = new ChessCoordinate(7, 2);
            threeStepsTowardsLongCastle = new ChessCoordinate(7, 1);

            canPotentiallyShortCastle = canPotentiallyShortCastle && options.isShortCastlingStillAvailableForWhite();
            canPotentiallyLongCastle = canPotentiallyLongCastle && options.isLongCastlingStillAvailableForWhite();
        } else {
            oneStepTowardsShortCastle = new ChessCoordinate(0, 5);
            twoStepsTowardsShortCastle = new ChessCoordinate(0, 6);

            oneStepTowardsLongCastle = new ChessCoordinate(0, 3);
            twoStepsTowardsLongCastle = new ChessCoordinate(0, 2);
            threeStepsTowardsLongCastle = new ChessCoordinate(0, 1);

            canPotentiallyShortCastle = canPotentiallyShortCastle && options.isShortCastlingStillAvailableForBlack();
            canPotentiallyLongCastle = canPotentiallyLongCastle && options.isLongCastlingStillAvailableForBlack();
        }

        if (canPotentiallyShortCastle &&
                chessBoardPiecesObserver.getChessPieceAt(oneStepTowardsShortCastle) == null &&
                chessBoardPiecesObserver.getChessPieceAt(twoStepsTowardsShortCastle) == null
        ) {
            boolean isOneStepGood = isThisStepGood(chessBoardPiecesObserver, currentCoordinate, oneStepTowardsShortCastle);
            boolean isTwoStepsGood = isOneStepGood && isThisStepGood(chessBoardPiecesObserver, currentCoordinate, twoStepsTowardsShortCastle);

            if (isTwoStepsGood) {
                registerNewCastlingMove(
                        chessMoveRegisterer,
                        currentCoordinate,
                        twoStepsTowardsShortCastle,
                        defaultShortCastlingRookCoordinate,
                        oneStepTowardsShortCastle,
                        ChessMoveType.SHORT_CASTLING
                );
            }
        }

        if (canPotentiallyLongCastle &&
                chessBoardPiecesObserver.getChessPieceAt(oneStepTowardsLongCastle) == null &&
                chessBoardPiecesObserver.getChessPieceAt(twoStepsTowardsLongCastle) == null &&
                chessBoardPiecesObserver.getChessPieceAt(threeStepsTowardsLongCastle) == null
        ) {
            boolean isOneStepGood = isThisStepGood(chessBoardPiecesObserver, currentCoordinate, oneStepTowardsLongCastle);
            boolean isTwoStepsGood = isOneStepGood && isThisStepGood(chessBoardPiecesObserver, currentCoordinate, twoStepsTowardsLongCastle);

            if (isTwoStepsGood) {
                registerNewCastlingMove(
                        chessMoveRegisterer,
                        currentCoordinate,
                        twoStepsTowardsLongCastle,
                        defaultLongCastlingRookCoordinate,
                        oneStepTowardsLongCastle,
                        ChessMoveType.LONG_CASTLING
                );
            }
        }

    }

    private static boolean isThisStepGood(ChessBoardPiecesObserver chessBoardPiecesObserver, ChessCoordinate currentCoordinate, ChessCoordinate oneStepTowardsLongCastle) {
        return !afterThisMoveIsPlayedCanTheKingBeTakenByEnemy(
                chessBoardPiecesObserver,
                ChessMoveInfo.builder()
                        .playedPieceMoveInfo(new PlayedPieceMoveInfo(currentCoordinate, oneStepTowardsLongCastle))
                        .chessMoveType(ChessMoveType.NORMAL_PIECE_MOVEMENT)
                        .build());
    }

    private static void registerNewCastlingMove(
            ChessMoveRegisterer chessMoveRegisterer,
            ChessCoordinate currentKingCoordinate,
            ChessCoordinate destinationKingCoordinate,
            ChessCoordinate currentRookCoordinate,
            ChessCoordinate destinationRookCoordinate,
            ChessMoveType chessMoveType
    ) {
        chessMoveRegisterer.registerNewChessMove(
                ChessMoveInfo.builder()
                        .playedPieceMoveInfo(new PlayedPieceMoveInfo(currentKingCoordinate, destinationKingCoordinate))
                        .triggeredPieceMoveInfos(Collections.singletonList(
                                new TriggeredPieceMoveInfo(
                                        currentRookCoordinate,
                                        destinationRookCoordinate
                                ))
                        ).chessMoveType(chessMoveType)
                        .build()
        );
    }
}
