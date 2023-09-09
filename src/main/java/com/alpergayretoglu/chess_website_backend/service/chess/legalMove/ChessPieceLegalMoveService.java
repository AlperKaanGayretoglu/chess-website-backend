package com.alpergayretoglu.chess_website_backend.service.chess.legalMove;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.ChessMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.pattern.PiecePattern;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessBoardPiecesObserver;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessMoveRegisterer;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
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
                boolean isCurrentPlayerInCheck,
                ChessBoardPiecesObserver chessBoardPiecesObserver,
                ChessCoordinate currentCoordinate,
                ChessMoveRegisterer chessMoveRegisterer,
                @Nullable ChessMove lastPlayedChessMove
        );
    }

    private static final Map<ChessPieceType, ChessPieceLegalMoveCalculator> chessPieceLegalMoveCalculatorMap;

    static {
        chessPieceLegalMoveCalculatorMap = new HashMap<>();
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.PAWN, PawnLegalMoveService::calculateLegalMovesForPawn);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.ROOK, ChessPieceLegalMoveService::calculateBasicMovement);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.KNIGHT, ChessPieceLegalMoveService::calculateBasicMovement);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.BISHOP, ChessPieceLegalMoveService::calculateBasicMovement);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.QUEEN, ChessPieceLegalMoveService::calculateBasicMovement);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.KING, ChessPieceLegalMoveService::calculateLegalMovesForKing);
    }

    public void calculateLegalMovesForPieceAtSquare(
            boolean isCurrentPlayerInCheck,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            ChessMoveRegisterer chessMoveRegisterer,
            @Nullable ChessMove lastPlayedChessMove
    ) {
        chessPieceLegalMoveCalculatorMap.get(chessBoardPiecesObserver.getChessPieceAt(currentCoordinate).getChessPieceType())
                .calculateLegalMoves(isCurrentPlayerInCheck, chessBoardPiecesObserver, currentCoordinate, chessMoveRegisterer, lastPlayedChessMove);
    }

    public static boolean isThisALegalMoveForPiece(
            boolean isCurrentPlayerInCheck,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate sourceCoordinate,
            ChessCoordinate targetCoordinate,
            @Nullable ChessMove lastPlayedChessMove
    ) {
        ChessMoveRegisterer chessMoveRegisterer = new ChessMoveRegisterer(null);
        chessPieceLegalMoveCalculatorMap.get(chessBoardPiecesObserver.getChessPieceAt(sourceCoordinate).getChessPieceType())
                .calculateLegalMoves(isCurrentPlayerInCheck, chessBoardPiecesObserver, sourceCoordinate, chessMoveRegisterer, lastPlayedChessMove);
        return chessMoveRegisterer.getPlayedPieceMoves().stream().anyMatch(chessMove -> chessMove.getTo().equals(targetCoordinate));
    }

    private static void calculateBasicMovement(
            boolean isCurrentPlayerInCheck,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            ChessMoveRegisterer chessMoveRegisterer,
            @Nullable ChessMove lastPlayedChessMove
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
                    chessMoveRegisterer.registerNewNormalCaptureMove(currentCoordinate, chessCoordinate);
                    return false;
                },
                chessCoordinate -> chessMoveRegisterer.registerNewNormalPieceMove(currentCoordinate, chessCoordinate)
        );
    }

    private static void calculateLegalMovesForKing(
            boolean isCurrentPlayerInCheck,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            ChessMoveRegisterer chessMoveRegisterer,
            @Nullable ChessMove lastPlayedChessMove
    ) {
        // TODO: Remove this method and implement it separately
        calculateBasicMovement(isCurrentPlayerInCheck, chessBoardPiecesObserver, currentCoordinate, chessMoveRegisterer, lastPlayedChessMove);
    }

}
