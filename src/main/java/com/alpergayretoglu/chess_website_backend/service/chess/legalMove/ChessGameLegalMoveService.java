package com.alpergayretoglu.chess_website_backend.service.chess.legalMove;

import com.alpergayretoglu.chess_website_backend.entity.chess.ChessCoordinate;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGame;
import com.alpergayretoglu.chess_website_backend.entity.chess.ChessGameState;
import com.alpergayretoglu.chess_website_backend.entity.chess.move.ChessMove;
import com.alpergayretoglu.chess_website_backend.repository.*;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessBoardPiecesObserver;
import com.alpergayretoglu.chess_website_backend.service.chess.ChessMoveRegisterer;
import com.alpergayretoglu.chess_website_backend.service.chess.legalMove.options.LegalMoveCalculatorOptions;
import com.alpergayretoglu.chess_website_backend.service.chess.legalMove.options.LegalMoveCalculatorStateOptions;
import com.alpergayretoglu.chess_website_backend.service.chess.legalMove.options.MoveCalculatorRequiredOptions;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log
public class ChessGameLegalMoveService {

    private final ChessPieceLegalMoveService chessPieceLegalMoveService;

    private final ChessMoveRepository chessMoveRepository;

    private final PlayedPieceMoveRepository playedPieceMoveRepository;
    private final TriggeredPieceMoveRepository triggeredPieceMoveRepository;
    private final PieceCaptureMoveRepository pieceCaptureMoveRepository;

    private final ChessGameRepository chessGameRepository;
    private final ChessGameStateRepository chessGameStateRepository;


    public void calculateAndSaveLegalMovesForCurrentPlayer(ChessGame chessGame, ChessBoardPiecesObserver chessBoardPiecesObserver, ChessMove lastPlayedChessMove) {
        List<ChessMove> chessMoves = chessMoveRepository.findAllByChessGame(chessGame);
        triggeredPieceMoveRepository.deleteAllByPartOfChessMoveIn(chessMoves);
        pieceCaptureMoveRepository.deleteAllByPartOfChessMoveIn(chessMoves);
        chessMoveRepository.deleteAll(chessMoves);

        ChessMoveRegisterer chessMoveRegisterer = new ChessMoveRegisterer(chessGame);

        boolean isCurrentPlayerInCheck = PlayerInCheckService.isPlayerWithColorInCheck(chessGame.getCurrentPlayerColor(), chessBoardPiecesObserver);

        ChessGameState chessGameState = chessGame.getChessGameState();
        chessGameState.setWhiteInCheck(false);
        chessGameState.setBlackInCheck(false);

        chessGameState.setCurrentPlayerInCheck(isCurrentPlayerInCheck);
        chessGameStateRepository.save(chessGameState);

        for (ChessCoordinate chessCoordinate : chessBoardPiecesObserver.getCoordinatesOfPiecesWithColor(chessGame.getCurrentPlayerColor())) {
            chessPieceLegalMoveService.calculateLegalMovesForPieceAtSquare(
                    LegalMoveCalculatorOptions.builder()
                            .legalMoveCalculatorStateOptions(
                                    LegalMoveCalculatorStateOptions.builder()
                                            .isCurrentPlayerInCheck(isCurrentPlayerInCheck)
                                            .lastPlayedChessMove(lastPlayedChessMove)
                                            .build()
                            )
                            .moveCalculatorRequiredOptions(
                                    MoveCalculatorRequiredOptions.builder()
                                            .chessBoardPiecesObserver(chessBoardPiecesObserver)
                                            .chessMoveRegisterer(chessMoveRegisterer)
                                            .build()
                            )
                            .forPieceAtCoordinate(chessCoordinate)
                            .build()
            );
        }

        chessMoveRepository.saveAll(chessMoveRegisterer.getLegalMoves());
        playedPieceMoveRepository.saveAll(chessMoveRegisterer.getPlayedPieceMoves());
        triggeredPieceMoveRepository.saveAll(chessMoveRegisterer.getTriggeredPieceMoves());
        pieceCaptureMoveRepository.saveAll(chessMoveRegisterer.getPieceCaptureMoves());

        chessGameRepository.save(chessGame);
    }

}
