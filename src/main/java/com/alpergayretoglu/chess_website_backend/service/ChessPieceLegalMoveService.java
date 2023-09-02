package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.PlayedPieceMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.pattern.PiecePattern;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alpergayretoglu.chess_website_backend.entity.chess.pattern.PiecePattern.chessPieceBasicMovePatternMap;

@Service
@NoArgsConstructor
public class ChessPieceLegalMoveService {
    @FunctionalInterface
    private interface ChessPieceLegalMoveCalculator {
        void calculateLegalMoves(
                ChessGame chessGame,
                ChessBoardPiecesObserver chessBoardPiecesObserver,
                ChessCoordinate currentCoordinate,
                List<ChessMove> legalMoves,
                List<PlayedPieceMove> playedPieceMoves
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
            ChessGame chessGame,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            List<ChessMove> legalMoves,
            List<PlayedPieceMove> playedPieceMoves
    ) {
        chessPieceLegalMoveCalculatorMap.get(chessBoardPiecesObserver.getChessPieceAt(currentCoordinate).getChessPieceType())
                .calculateLegalMoves(chessGame, chessBoardPiecesObserver, currentCoordinate, legalMoves, playedPieceMoves);
    }

    private static void calculateBasicMovement(
            ChessGame chessGame,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            List<ChessMove> legalMoves,
            List<PlayedPieceMove> playedPieceMoves
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
                chessCoordinate -> {
                    ChessMove chessMove = ChessMove.builder().chessGame(chessGame).build();
                    legalMoves.add(chessMove);

                    PlayedPieceMove playedPieceMove = new PlayedPieceMove(chessMove, currentCoordinate, chessCoordinate);
                    playedPieceMoves.add(playedPieceMove);
                }
        );
    }

    private static void calculateLegalMovesForPawn(
            ChessGame chessGame,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            List<ChessMove> legalMoves,
            List<PlayedPieceMove> playedPieceMoves
    ) {
        ChessColor pawnColor = chessBoardPiecesObserver.getChessPieceAt(currentCoordinate).getChessColor();
        // TODO: Improve this method

        if (pawnColor == ChessColor.WHITE) {
            boolean isPawnAtStartingPosition = currentCoordinate.getRow() == 1;

            int oneStepForwardRow = currentCoordinate.getRow() + 1;
            int twoStepsForwardRow = currentCoordinate.getRow() + 2;

            boolean isOneStepForwardExists = ChessCoordinate.isCoordinateValid(oneStepForwardRow, currentCoordinate.getColumn());
            boolean doesTwoStepsForwardExists = ChessCoordinate.isCoordinateValid(twoStepsForwardRow, currentCoordinate.getColumn());

            if (isOneStepForwardExists) {
                ChessCoordinate oneStepForwardCoordinate = new ChessCoordinate(oneStepForwardRow, currentCoordinate.getColumn());

                if (chessBoardPiecesObserver.getChessPieceAt(oneStepForwardCoordinate) == null) {
                    ChessMove chessMove = ChessMove.builder().chessGame(chessGame).build();
                    legalMoves.add(chessMove);

                    PlayedPieceMove playedPieceMove = new PlayedPieceMove(chessMove, currentCoordinate, oneStepForwardCoordinate);
                    playedPieceMoves.add(playedPieceMove);

                    if (isPawnAtStartingPosition && doesTwoStepsForwardExists) {
                        ChessCoordinate twoStepsForwardCoordinate = new ChessCoordinate(twoStepsForwardRow, currentCoordinate.getColumn());
                        if (chessBoardPiecesObserver.getChessPieceAt(twoStepsForwardCoordinate) == null) {
                            ChessMove chessMove2 = ChessMove.builder().chessGame(chessGame).build();
                            legalMoves.add(chessMove2);

                            PlayedPieceMove playedPieceMove2 = new PlayedPieceMove(chessMove2, currentCoordinate, twoStepsForwardCoordinate);
                            playedPieceMoves.add(playedPieceMove2);
                        }
                    }
                }
            }
        } else {
            boolean isPawnAtStartingPosition = currentCoordinate.getRow() == 6;

            int oneStepForwardRow = currentCoordinate.getRow() - 1;
            int twoStepsForwardRow = currentCoordinate.getRow() - 2;

            boolean isOneStepForwardExists = ChessCoordinate.isCoordinateValid(oneStepForwardRow, currentCoordinate.getColumn());
            boolean doesTwoStepsForwardExists = ChessCoordinate.isCoordinateValid(twoStepsForwardRow, currentCoordinate.getColumn());

            if (isOneStepForwardExists) {
                ChessCoordinate oneStepForwardCoordinate = new ChessCoordinate(oneStepForwardRow, currentCoordinate.getColumn());

                if (chessBoardPiecesObserver.getChessPieceAt(oneStepForwardCoordinate) == null) {
                    ChessMove chessMove = ChessMove.builder().chessGame(chessGame).build();
                    legalMoves.add(chessMove);

                    PlayedPieceMove playedPieceMove = new PlayedPieceMove(chessMove, currentCoordinate, oneStepForwardCoordinate);
                    playedPieceMoves.add(playedPieceMove);

                    if (isPawnAtStartingPosition && doesTwoStepsForwardExists) {
                        ChessCoordinate twoStepsForwardCoordinate = new ChessCoordinate(twoStepsForwardRow, currentCoordinate.getColumn());
                        if (chessBoardPiecesObserver.getChessPieceAt(twoStepsForwardCoordinate) == null) {
                            ChessMove chessMove2 = ChessMove.builder().chessGame(chessGame).build();
                            legalMoves.add(chessMove2);

                            PlayedPieceMove playedPieceMove2 = new PlayedPieceMove(chessMove2, currentCoordinate, twoStepsForwardCoordinate);
                            playedPieceMoves.add(playedPieceMove2);
                        }
                    }
                }
            }
        }
    }

    private static void calculateLegalMovesForKing(
            ChessGame chessGame,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            List<ChessMove> legalMoves,
            List<PlayedPieceMove> playedPieceMoves
    ) {
        // TODO: Remove this method and implement it separately
        calculateBasicMovement(chessGame, chessBoardPiecesObserver, currentCoordinate, legalMoves, playedPieceMoves);
    }

}
