package com.alpergayretoglu.chess_website_backend.service.chess.legalMove;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.pattern.PiecePattern;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessBoardPiecesObserver;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessMoveRegisterer;
import com.alpergayretoglu.chess_website_backend.service.chess.legalMove.options.LegalMoveCalculatorOptions;
import com.alpergayretoglu.chess_website_backend.service.chess.legalMove.options.LegalMoveCalculatorStateOptions;
import com.alpergayretoglu.chess_website_backend.service.chess.legalMove.options.MoveCalculatorRequiredOptions;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static com.alpergayretoglu.chess_website_backend.entity.chess.pattern.PiecePattern.chessPieceBasicMovePatternMap;

@Service
@NoArgsConstructor
public class ChessPieceLegalMoveService {
    private static final Map<ChessPieceType, Consumer<LegalMoveCalculatorOptions>> chessPieceLegalMoveCalculatorMap;

    static {
        chessPieceLegalMoveCalculatorMap = new HashMap<>();
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.PAWN, PawnLegalMoveService::calculateLegalMovesForPawn);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.ROOK, ChessPieceLegalMoveService::calculateBasicMovement);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.KNIGHT, ChessPieceLegalMoveService::calculateBasicMovement);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.BISHOP, ChessPieceLegalMoveService::calculateBasicMovement);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.QUEEN, ChessPieceLegalMoveService::calculateBasicMovement);
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.KING, ChessPieceLegalMoveService::calculateLegalMovesForKing);
    }

    public void calculateLegalMovesForPieceAtSquare(LegalMoveCalculatorOptions options) {
        ChessBoardPiecesObserver chessBoardPiecesObserver = options.getChessBoardPiecesObserver();
        ChessCoordinate currentCoordinate = options.getForPieceAtCoordinate();

        chessPieceLegalMoveCalculatorMap.get(
                chessBoardPiecesObserver.getChessPieceAt(currentCoordinate).getChessPieceType()
        ).accept(options);
    }

    public static boolean isThisALegalMoveForPiece(
            LegalMoveCalculatorStateOptions legalMoveCalculatorStateOptions,
            ChessBoardPiecesObserver chessBoardPiecesObserver,
            ChessCoordinate sourceCoordinate,
            ChessCoordinate targetCoordinate
    ) {
        ChessMoveRegisterer chessMoveRegisterer = new ChessMoveRegisterer(null);

        LegalMoveCalculatorOptions options = LegalMoveCalculatorOptions.builder()
                .legalMoveCalculatorStateOptions(legalMoveCalculatorStateOptions)
                .moveCalculatorRequiredOptions(
                        MoveCalculatorRequiredOptions.builder()
                                .chessBoardPiecesObserver(chessBoardPiecesObserver)
                                .chessMoveRegisterer(chessMoveRegisterer)
                                .build()
                )
                .forPieceAtCoordinate(sourceCoordinate)
                .build();

        chessPieceLegalMoveCalculatorMap.get(
                chessBoardPiecesObserver.getChessPieceAt(sourceCoordinate).getChessPieceType()
        ).accept(options);

        return chessMoveRegisterer.getPlayedPieceMoves().stream().anyMatch(chessMove -> chessMove.getTo().equals(targetCoordinate));
    }

    private static void calculateBasicMovement(LegalMoveCalculatorOptions options) {
        ChessBoardPiecesObserver chessBoardPiecesObserver = options.getChessBoardPiecesObserver();
        ChessCoordinate currentCoordinate = options.getForPieceAtCoordinate();
        ChessMoveRegisterer chessMoveRegisterer = options.getChessMoveRegisterer();

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

    private static void calculateLegalMovesForKing(LegalMoveCalculatorOptions options) {
        // TODO: Remove this method and implement it separately
        calculateBasicMovement(options);
    }

}
