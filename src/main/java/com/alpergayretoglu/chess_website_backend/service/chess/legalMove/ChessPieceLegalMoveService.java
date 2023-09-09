package com.alpergayretoglu.chess_website_backend.service.chess.legalMove;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.PieceCaptureMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.PlayedPieceMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.TriggeredPieceMove;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.info.ChessMoveInfo;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.info.PieceCaptureMoveInfo;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.info.PlayedPieceMoveInfo;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.info.TriggeredPieceMoveInfo;
import com.alpergayretoglu.chess_website_backend.entity.chess.pattern.PiecePattern;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessColor;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPiece;
import com.alpergayretoglu.chess_website_backend.model.enums.ChessPieceType;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessBoardPiecesModifier;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessBoardPiecesObserver;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessMoveRegisterer;
import com.alpergayretoglu.chess_website_backend.service.chess.legalMove.options.LegalMoveCalculatorOptions;
import com.alpergayretoglu.chess_website_backend.service.chess.legalMove.options.LegalMoveCalculatorStateOptions;
import com.alpergayretoglu.chess_website_backend.service.chess.legalMove.options.MoveCalculatorRequiredOptions;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
        chessPieceLegalMoveCalculatorMap.put(ChessPieceType.KING, KingLegalMoveService::calculateLegalMovesForKing);
    }

    public void calculateLegalMovesForPieceAtSquare(LegalMoveCalculatorOptions options) {
        ChessBoardPiecesObserver chessBoardPiecesObserver = options.getChessBoardPiecesObserver();
        ChessCoordinate currentCoordinate = options.getForPieceAtCoordinate();

        if (options.isShouldConsiderSafetyOfTheKing()) {
            options.getChessMoveRegisterer().addFilter(chessMoveInfo ->
                    !afterThisMoveIsPlayedCanTheKingBeTakenByEnemy(
                            chessBoardPiecesObserver,
                            chessMoveInfo
                    ));
        }

        chessPieceLegalMoveCalculatorMap.get(
                chessBoardPiecesObserver.getChessPieceAt(currentCoordinate).getChessPieceType()
        ).accept(options);

        options.getChessMoveRegisterer().removeFilter();

        // TODO: Ability to add and remove filters is bad design, since you could forget to remove the filter. Find a better way to handle this.
        //  Initially you made a new constructor with filter field and then merged the two registers when you were done, but the merging was not working properly.
        //  because it is not that easy to merge two ChessMoveRegisterer objects.
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

    static void calculateBasicMovement(LegalMoveCalculatorOptions options) {
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

    static boolean afterThisMoveIsPlayedCanTheKingBeTakenByEnemy(ChessBoardPiecesObserver chessBoardPiecesObserver, ChessMoveInfo chessMoveInfo) {
        ChessColor color = chessBoardPiecesObserver.getChessPieceAt(chessMoveInfo.getPlayedPieceMoveInfo().getPlayedPieceFrom()).getChessColor();

        ChessBoardPiecesModifier chessBoardPiecesModifier = chessBoardPiecesObserver.createNewIndependentModifier();

        PlayedPieceMoveInfo playedPieceMoveInfo = chessMoveInfo.getPlayedPieceMoveInfo();
        PlayedPieceMove playedPieceMove = new PlayedPieceMove(null, playedPieceMoveInfo.getPlayedPieceFrom(), playedPieceMoveInfo.getPlayedPieceTo());

        List<TriggeredPieceMoveInfo> triggeredPieceMoveInfos = chessMoveInfo.getTriggeredPieceMoveInfos();
        List<TriggeredPieceMove> triggeredPieceMoves = triggeredPieceMoveInfos.stream()
                .map(triggeredPieceMoveInfo ->
                        new TriggeredPieceMove(null, triggeredPieceMoveInfo.getTriggeredPieceFrom(), triggeredPieceMoveInfo.getTriggeredPieceTo()))
                .collect(Collectors.toList());

        List<PieceCaptureMoveInfo> pieceCaptureMoveInfos = chessMoveInfo.getPieceCaptureMoveInfos();
        List<PieceCaptureMove> pieceCaptureMoves = pieceCaptureMoveInfos.stream()
                .map(pieceCaptureMoveInfo -> new PieceCaptureMove(null, pieceCaptureMoveInfo.getPieceCaptureFrom()))
                .collect(Collectors.toList());

        chessBoardPiecesModifier.playChessMove(playedPieceMove, triggeredPieceMoves, pieceCaptureMoves);

        ChessBoardPiecesObserver chessBoardPiecesObserverAfterMove = chessBoardPiecesModifier.turnIntoObserver();

        ChessCoordinate kingCoordinate = chessBoardPiecesObserverAfterMove.getCoordinatesOfPiecesWithColor(color).stream()
                .filter(chessCoordinate -> chessBoardPiecesObserverAfterMove.getChessPieceAt(chessCoordinate).getChessPieceType() == ChessPieceType.KING)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("There is no king on the board"));

        List<ChessCoordinate> enemyCoordinates = chessBoardPiecesObserverAfterMove.getCoordinatesOfPiecesWithColor(color.getOppositeColor());
        for (ChessCoordinate enemyCoordinate : enemyCoordinates) {
            if (isThisALegalMoveForPiece(
                    LegalMoveCalculatorStateOptions.builder()
                            .isCurrentPlayerInCheck(false)
                            .lastPlayedChessMove(null)
                            .shouldConsiderSafetyOfTheKing(false)
                            .build(),
                    chessBoardPiecesObserverAfterMove,
                    enemyCoordinate,
                    kingCoordinate
            )) {
                return true;
            }
        }

        return false;
    }

}
