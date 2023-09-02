package com.alpergayretoglu.chess_website_backend.service;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.PlayedPieceMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.movePattern.MovePattern;
import com.alpergayretoglu.chess_website_backend.entity.chess.movePattern.MovePatternIterator;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final Map<ChessPieceType, MovePattern> chessPieceBasicMovePatternMap;

    static {
        chessPieceLegalMoveCalculatorMap = new HashMap<>();
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.PAWN, ChessPieceLegalMoveService::calculateLegalMovesForPawn);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.ROOK, ChessPieceLegalMoveService::calculateLegalMovesForRook);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.KNIGHT, ChessPieceLegalMoveService::calculateLegalMovesForKnight);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.BISHOP, ChessPieceLegalMoveService::calculateLegalMovesForBishop);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.QUEEN, ChessPieceLegalMoveService::calculateLegalMovesForQueen);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.KING, ChessPieceLegalMoveService::calculateLegalMovesForKing);

        chessPieceBasicMovePatternMap = new HashMap<>();
        chessPieceBasicMovePatternMap.put(ChessPieceType.PAWN, new MovePattern(1, new int[][]{
                {0, 1}
        }));
        chessPieceBasicMovePatternMap.put(ChessPieceType.KNIGHT, new MovePattern(1, new int[][]{
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}, {2, 1}, {2, -1}, {-2, 1}, {-2, -1}
        }));
        chessPieceBasicMovePatternMap.put(ChessPieceType.BISHOP, new MovePattern(new int[][]{
                {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        }));
        chessPieceBasicMovePatternMap.put(ChessPieceType.ROOK, new MovePattern(new int[][]{
                {1, 0}, {0, 1}, {-1, 0}, {0, -1}
        }));
        chessPieceBasicMovePatternMap.put(ChessPieceType.QUEEN, new MovePattern(new int[][]{
                {1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {1, 0}, {0, 1}, {-1, 0}, {0, -1}
        }));
        chessPieceBasicMovePatternMap.put(ChessPieceType.KING, new MovePattern(1, new int[][]{
                {1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {1, 0}, {0, 1}, {-1, 0}, {0, -1}
        }));
    }

    public void calculateLegalMovesForPieceAtSquare(
            ChessGame chessGame,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            List<ChessMove> legalMoves,
            List<PlayedPieceMove> playedPieceMoves
    ) {
        ChessPiece chessPiece = chessBoardPiecesObserver.getChessPieceAt(currentCoordinate);
        MovePatternIterator movePatternIterator = chessPieceBasicMovePatternMap.get(chessPiece.getChessPieceType()).getIteratorStartingAt(currentCoordinate);

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

        chessPieceLegalMoveCalculatorMap.get(chessBoardPiecesObserver.getChessPieceAt(currentCoordinate).getChessPieceType())
                .calculateLegalMoves(chessGame, chessBoardPiecesObserver, currentCoordinate, legalMoves, playedPieceMoves);
    }

    private static void calculateLegalMovesForPawn(
            ChessGame chessGame,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            List<ChessMove> legalMoves,
            List<PlayedPieceMove> playedPieceMoves
    ) {

    }

    private static void calculateLegalMovesForRook(
            ChessGame chessGame,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            List<ChessMove> legalMoves,
            List<PlayedPieceMove> playedPieceMoves
    ) {

    }

    private static void calculateLegalMovesForKnight(
            ChessGame chessGame,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            List<ChessMove> legalMoves,
            List<PlayedPieceMove> playedPieceMoves
    ) {

    }

    private static void calculateLegalMovesForBishop(
            ChessGame chessGame,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            List<ChessMove> legalMoves,
            List<PlayedPieceMove> playedPieceMoves
    ) {

    }

    private static void calculateLegalMovesForQueen(
            ChessGame chessGame,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            List<ChessMove> legalMoves,
            List<PlayedPieceMove> playedPieceMoves
    ) {

    }

    private static void calculateLegalMovesForKing(
            ChessGame chessGame,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate currentCoordinate,
            List<ChessMove> legalMoves,
            List<PlayedPieceMove> playedPieceMoves
    ) {

    }

}
